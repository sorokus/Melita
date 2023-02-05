package com.melita.ordermanagement.service.impl;

import com.melita.ordermanagement.base.exceptions.SystemException;
import com.melita.ordermanagement.model.convertor.OrderConvertor;
import com.melita.ordermanagement.model.convertor.ProductConvertor;
import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.model.dto.ProductDto;
import com.melita.ordermanagement.model.entity.Order;
import com.melita.ordermanagement.model.entity.Product;
import com.melita.ordermanagement.repository.OrderRepository;
import com.melita.ordermanagement.repository.ProductRepository;
import com.melita.ordermanagement.service.EmailService;
import com.melita.ordermanagement.service.OrderProcessingService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * @author sorokus.dev@gmail.com
 */

@Service
public class OrderProcessingServiceimpl implements OrderProcessingService {

    private final static String APPROVED_BY_SYSTEM = "system";

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtx;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderConvertor orderConvertor;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductConvertor productConvertor;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("ofRestTemplate")
    private RestTemplate restTemplate;

    @RabbitListener(queues = "${amqp.queue.name}")
    public void pickOrder(OrderDto orderData) {
        Order order = orderConvertor.convertFromDtoToEntity(orderData);

        List<Product> approvableProducts = productRepository.findDistinctByPackagesIsInAndIsApprovable(orderData.getPackageIds(),
                                                                                                       true);
        // if no products in the order require approval, so Order can be
        // - approved by the system
        // - saved into DB
        // - submitted into Ordering Fulfilment system restfully
        // otherwise
        // - saved into DB
        // - Send the email to the Agent for Order approval

        if (approvableProducts.size() == 0) {
            order.setApprovedAt(new Date());
            order.setApprovedBy(APPROVED_BY_SYSTEM);
        }

        try {
            order = orderRepository.save(order);
        }
        catch (Exception exc) {
            //TODO: On JPA error - rabbit executes infinitely - handle ex;
            // TODO: If persistence error happens, put Order in DQL or log or whatever place for further reprocessing.
            // Make this improvement further
            exc.printStackTrace();
        }

        // Email to the Agent on new Order placement
        List<ProductDto> selProducts = fetchAndfilterSelectedProductsAndPackages(orderData);
        try {
            emailService.sendNewOrderHtmlMail(order.getId(), orderData, selProducts);
        }
        catch (SystemException e) {
            e.printStackTrace();
        }

        if (approvableProducts.size() == 0) {
            // Submit into Ordering Fulfilment system restfully
            try {
                submitIntoOrderFulfillmentSystem(orderData);
            }
            catch (SystemException se) {
                // TODO:
                se.printStackTrace();
            }
        }
        else {
            // Email to the Agent on a need of new Order approval due to selected products/packages requiring approval

            String link = "http://"
                    + InetAddress.getLoopbackAddress().getHostName()
                    + ":"
                    + webServerAppCtx.getWebServer().getPort()
                    + "/api/v1/ordermanagement/approval/approveOrder/" +
                    order.getId();
            try {

                emailService.sendNewOrderForApprovalHtmlMail(order.getId(),
                                                             orderData,
                                                             selProducts,
                                                             filterSelectedProductsAndPackages(approvableProducts,
                                                                                               orderData.getPackageIds()),
                                                             link);
            }
            catch (SystemException e) {
                e.printStackTrace();
            }

        }
        System.out.println(orderData);
    }

    public void submitIntoOrderFulfillmentSystem(OrderDto orderData) throws SystemException {
        try {
            ResponseEntity<OrderDto> result = restTemplate.postForEntity(new URI("${app.ordering-fulfilment.url}"),
                                                                         orderData,
                                                                         OrderDto.class);
        }
        catch (URISyntaxException e) {
            throw new SystemException("Bad configuration. Wrong URL to OrderFulfillmentSystem", e);
        }
    }

    private List<ProductDto> fetchAndfilterSelectedProductsAndPackages(OrderDto orderData) {
        final Set<Long> packageIds = orderData.getPackageIds();
        List<Product> selectedProducts = productRepository.findDistinctByPackagesIsIn(packageIds);
        return filterSelectedProductsAndPackages(selectedProducts, packageIds);
    }

    private List<ProductDto> filterSelectedProductsAndPackages(List<Product> selectedProducts, Set<Long> packageIds) {
        List<ProductDto> productDtos = selectedProducts.stream().map(p -> productConvertor.convertToDto(p)).toList();
        productDtos.forEach(p -> p.setPackages(p.getPackages()
                                                .stream()
                                                .filter(pack -> packageIds.contains(pack.getId()))
                                                .toList()));
        return productDtos;
    }
}
