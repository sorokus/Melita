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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderProcessingServiceimpl.class);

    private final static String APPROVED_BY_SYSTEM = "system";

    @Value("${app.ordering-fulfilment.url}")
    private String orderingFulfilmentUrl;

    private EmailService                       emailService;
    private OrderConvertor                     orderConvertor;
    private OrderRepository                    orderRepository;
    private ProductConvertor                   productConvertor;
    private ProductRepository                  productRepository;
    private RestTemplate                       restTemplate;
    private ServletWebServerApplicationContext webServerAppCtx;

    @RabbitListener(queues = "${amqp.queue.name}")
    public void pickOrder(OrderDto orderData) {
        LOGGER.debug("A new Order picked up from the Queue.");

        List<Product> approvableProducts = productRepository.findDistinctByPackagesIsInAndIsApprovable(orderData.getPackageIds(),
                                                                                                       true);
        // if no products in the order require approval, so Order can be
        // - auto approved by the system
        // - saved into DB
        // - submitted into Ordering Fulfilment system restfully
        // otherwise
        // - saved into DB
        // - Send the email to the Agent for Order approval

        Order order = orderConvertor.convertFromDtoToEntity(orderData);

        if (approvableProducts.size() == 0) {
            order.setApprovedAt(new Date());
            order.setApprovedBy(APPROVED_BY_SYSTEM);
        }

        LOGGER.debug("A new Order being persisted in DB.");

        try {
            order = orderRepository.save(order);
        }
        catch (Exception exc) {
            // If persistence error happens, put Order in DLQ or log or whatever place for further reprocessing.
            // Make this improvement further.
            LOGGER.error("Error persisting the Order ", exc);
            return;
        }

        // Email to the Agent on new Order placement
        List<ProductDto> selProducts = fetchAndFilterSelectedProductsAndPackages(orderData);
        LOGGER.debug("A notification email on the new Order #" + order.getId() + " being sent");
        try {
            emailService.sendNewOrderHtmlMail(order.getId(), orderData, selProducts);
        }
        catch (SystemException se) {
            LOGGER.error("Error sending notification email on the new Order #" + order.getId(), se);
            return;
        }

        if (approvableProducts.size() == 0) {
            // Submit into Ordering Fulfilment system restfully
            try {
                submitIntoOrderFulfillmentSystem(orderData);
            }
            catch (SystemException se) {
                LOGGER.error("Error submitting the Order into OrderFulfillment System", se);
                return;
            }
        }
        else {
            // Email to the Agent on a need of new Order approval due to selected products/packages requiring approval

            String approvalLink = "http://"
                    + InetAddress.getLoopbackAddress().getHostName()
                    + ":"
                    + webServerAppCtx.getWebServer().getPort()
                    + "/api/v1/ordermanagement/approval/approveOrder/" +
                    order.getId();
            try {
                LOGGER.debug("An email to Agent for approval of Order #" + order.getId() + " being sent");
                emailService.sendNewOrderForApprovalHtmlMail(order.getId(),
                                                             orderData,
                                                             selProducts,
                                                             filterSelectedProductsAndPackages(approvableProducts,
                                                                                               orderData.getPackageIds()),
                                                             approvalLink);
            }
            catch (SystemException se) {
                LOGGER.error("Error sending an email to Agent for approval of Order #" + order.getId(), se);
                return;
            }
        }
        LOGGER.debug("The new Order #" + order.getId() + " is fully processed");
    }

    public void submitIntoOrderFulfillmentSystem(OrderDto orderData) throws SystemException {
        LOGGER.debug("Submission to OrderFulfillment System is initiated");
        URI uri;
        try {
            uri = new URI(orderingFulfilmentUrl);
        }
        catch (URISyntaxException e) {
            throw new SystemException("Bad configuration. Wrong URL to OrderFulfillment System", e);
        }
        try {
            // Make a further restful submission of Order into OrderFulfillmentSystem vie POST method.
            // Since OrderFulfillmentSystem's URL/API is not defined, it can be done with a below pseudocode.

            //            ResponseEntity<OrderDto> result = restTemplate.postForEntity(uri,
            //                                                                         orderData,
            //                                                                         OrderDto.class);
        }
        catch (RestClientException rce) {
            throw new SystemException("Error submitting data into OrderFulfillmentSystem", rce);
        }
        LOGGER.debug("Submission to OrderFulfillment System is completed");
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setOrderConvertor(OrderConvertor orderConvertor) {
        this.orderConvertor = orderConvertor;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setProductConvertor(ProductConvertor productConvertor) {
        this.productConvertor = productConvertor;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    @Qualifier("ofRestTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setWebServerAppCtx(ServletWebServerApplicationContext webServerAppCtx) {
        this.webServerAppCtx = webServerAppCtx;
    }

    private List<ProductDto> fetchAndFilterSelectedProductsAndPackages(OrderDto orderData) {
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
