package com.melita.ordermanagement.service.impl;

import com.melita.ordermanagement.base.exceptions.BusinessException;
import com.melita.ordermanagement.base.exceptions.SystemException;
import com.melita.ordermanagement.model.convertor.OrderConvertor;
import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.model.entity.Order;
import com.melita.ordermanagement.repository.OrderRepository;
import com.melita.ordermanagement.repository.ProductRepository;
import com.melita.ordermanagement.service.OrderApprovalService;
import com.melita.ordermanagement.service.OrderProcessingService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderApprovalServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderConvertor  orderConvertor;

    @Mock
    private OrderProcessingService orderProcessingService;

    @InjectMocks
    private OrderApprovalService orderApprovalService = new OrderApprovalServiceImpl();

    @Test
    void approveOrder() throws BusinessException, SystemException {
        Order order = new Order();
        OrderDto orderDto = new OrderDto();
        when(orderRepository.findByIdAndApprovedByIsNull(1L)).thenReturn(Optional.of(order));
        when(orderConvertor.convertToDto(order)).thenReturn(orderDto);
        orderApprovalService.approveOrder(1L, "approver");
        verify(orderRepository).findByIdAndApprovedByIsNull(1L);
        verify(orderProcessingService).submitIntoOrderFulfillmentSystem(orderDto);
    }
}