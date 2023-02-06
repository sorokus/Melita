package com.melita.ordermanagement.service.impl;

import com.melita.ordermanagement.base.exceptions.SystemException;
import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.repository.ProductRepository;
import com.melita.ordermanagement.service.OrderPlacementService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderPlacementServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderPlacementService orderPlacementService = new OrderPlacementServiceImpl();

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(orderPlacementService, "exchangeName", "exchangeName");
        ReflectionTestUtils.setField(orderPlacementService, "routingKey", "routingKey");
    }

    @Test
    void getAvailableProductsWithPackages() {
        orderPlacementService.getAvailableProductsWithPackages();
        verify(productRepository).findAllByOrderByIdAsc();
    }

    @Test
    void placeOrder() throws SystemException {
        orderPlacementService.placeOrder(new OrderDto());
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), any(OrderDto.class));
    }
}