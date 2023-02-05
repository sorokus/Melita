package com.melita.ordermanagement.model.convertor;

import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.model.entity.Order;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/*
 * @author sorokus.dev@gmail.com
 */

@Component
public class OrderConvertor {

    private final ModelMapper modelMapper;

    public OrderConvertor() {
        this.modelMapper = new ModelMapper();
        modelMapper.typeMap(Order.class, OrderDto.class);
        modelMapper.typeMap(OrderDto.class, Order.class);
    }

    public OrderDto convertToDto(Order entity) {
        return modelMapper.map(entity, OrderDto.class);
    }

    public Order convertFromDtoToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }
}