package com.melita.ordermanagement.model.convertor;
/*
 * @author sorokus.dev@gmail.com
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.melita.ordermanagement.model.dto.OrderDto;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConvertor {

//    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

//    public OrderConvertor() {
//        this.modelMapper = new ModelMapper();
//    }

    public String convertFromDtoToJSON(OrderDto orderDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(orderDto);
    }

//    public Order convertFromDtoToEntity(OrderDto orderDto)  {
//
//    }
}