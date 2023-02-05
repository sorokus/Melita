package com.melita.ordermanagement.service.impl;

import com.melita.ordermanagement.base.exceptions.BusinessException;
import com.melita.ordermanagement.base.exceptions.SystemException;
import com.melita.ordermanagement.model.convertor.OrderConvertor;
import com.melita.ordermanagement.model.entity.Order;
import com.melita.ordermanagement.repository.OrderRepository;
import com.melita.ordermanagement.service.OrderApprovalService;
import com.melita.ordermanagement.service.OrderProcessingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import jakarta.persistence.PersistenceException;

/*
 * @author sorokus.dev@gmail.com
 */

@Service
public class OrderApprovalServiceimpl implements OrderApprovalService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderApprovalServiceimpl.class);

    private OrderConvertor         orderConvertor;
    private OrderProcessingService orderProcessingService;
    private OrderRepository        orderRepository;

    @Override
    public void approveOrder(Long orderId, String approverName) throws BusinessException, SystemException {
        LOGGER.debug("Approving the Order #" + orderId + " by " + approverName);

        Order order = orderRepository.findByIdAndApprovedByIsNull(orderId)
                                     .orElseThrow(() -> new BusinessException("Order with id=" + orderId + " not found or doesn't require approval"));
        order.setApprovedAt(new Date());
        order.setApprovedBy(approverName);
        try {
            orderRepository.save(order);
        }
        catch (PersistenceException pe) {
            throw new SystemException("Exception during order persistence", pe);
        }

        // Once order (approvable products/packages) is approved - submit into Ordering Fulfilment system restfully
        LOGGER.debug("Submitting approved Order #" + orderId + " into OrderFulfillment System");
        orderProcessingService.submitIntoOrderFulfillmentSystem(orderConvertor.convertToDto(order));
    }

    @Autowired
    public void setOrderConvertor(OrderConvertor orderConvertor) {
        this.orderConvertor = orderConvertor;
    }

    @Autowired
    public void setOrderProcessingService(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

}
