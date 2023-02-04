package com.melita.ordermanagement.service.impl;

import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.model.entity.Product;
import com.melita.ordermanagement.repository.ProductRepository;
import com.melita.ordermanagement.service.EmailService;
import com.melita.ordermanagement.service.OrderProcessingService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author sorokus.dev@gmail.com
 */

@Service
public class OrderProcessingServiceimpl implements OrderProcessingService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @RabbitListener(queues = "${amqp.queue.name}")
    public void pickOrder(OrderDto orderData) {
        emailService.sendHtmlMail(orderData);
        List<Product> approvableProducts = productRepository.findDistinctByPackagesIsInAndIsApprovable(orderData.getPackageIds(), true);

        if (approvableProducts.size()==0) {
            // No products in the order require approval, so order can be submitted into Ordering Fulfilment system
        }
        System.out.println(orderData);
    }

}
