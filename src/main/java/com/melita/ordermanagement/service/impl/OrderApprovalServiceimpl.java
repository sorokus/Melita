package com.melita.ordermanagement.service.impl;

import com.melita.ordermanagement.base.exceptions.BusinessException;
import com.melita.ordermanagement.base.exceptions.SystemException;
import com.melita.ordermanagement.model.convertor.OrderConvertor;
import com.melita.ordermanagement.model.entity.Order;
import com.melita.ordermanagement.repository.OrderRepository;
import com.melita.ordermanagement.service.OrderApprovalService;
import com.melita.ordermanagement.service.OrderProcessingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import jakarta.persistence.PersistenceException;

/*
 * @author sorokus.dev@gmail.com
 */

@Service
public class OrderApprovalServiceimpl implements OrderApprovalService {

    @Autowired
    private OrderProcessingService orderProcessingService;

    @Autowired
    private OrderConvertor orderConvertor;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void approveOrder(Long orderId, String approverName) throws BusinessException, SystemException {
        Order order = orderRepository.findByIdAndApprovedByIsNull(orderId)
                                     .orElseThrow(() -> new BusinessException("Order with id=" + orderId + " not found or doesn't require approval"));
        order.setApprovedAt(new Date());
        order.setApprovedBy(approverName);
        try {
            orderRepository.save(order);
        } catch (PersistenceException pe) {
            throw new SystemException("Exception during order persistence", pe);
        }

        // Once order (approvabke products/packages) is approved - submit into Ordering Fulfilment system restfully
        orderProcessingService.submitIntoOrderFulfillmentSystem(orderConvertor.convertToDto(order));
    }
}
