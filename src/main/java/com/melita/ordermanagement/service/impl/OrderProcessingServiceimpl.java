package com.melita.ordermanagement.service.impl;

import com.melita.ordermanagement.model.convertor.OrderConvertor;
import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.model.entity.Order;
import com.melita.ordermanagement.model.entity.Product;
import com.melita.ordermanagement.repository.OrderRepository;
import com.melita.ordermanagement.repository.ProductRepository;
import com.melita.ordermanagement.service.EmailService;
import com.melita.ordermanagement.service.OrderProcessingService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
 * @author sorokus.dev@gmail.com
 */

@Service
public class OrderProcessingServiceimpl implements OrderProcessingService {

    private final static String APPROVED_BY_SYSTEM = "system";

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderConvertor orderConvertor;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
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
            orderRepository.save(order);
        }
        catch (Exception exc) {
            //TODO: On JPA error - rabbit executes infinitely - handle ex;
            // TODO: If persistence error happens, put Order in DQL or log or whatever place for further reprocessing.
            // Make this improvement further
            exc.printStackTrace();
        }

        // Send an email to the Agent on new Order placement
        //emailService.sendHtmlMail(orderData);

        if (approvableProducts.size() == 0) {
            // submitt into Ordering Fulfilment system restfully
        } else {
            //emailService.sendHtmlMail(orderData);
        }
        System.out.println(orderData);
    }

}
