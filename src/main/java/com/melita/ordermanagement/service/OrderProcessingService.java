package com.melita.ordermanagement.service;

/*
 * @author sorokus.dev@gmail.com
 */

import com.melita.ordermanagement.base.exceptions.SystemException;
import com.melita.ordermanagement.model.dto.OrderDto;

public interface OrderProcessingService {

    void submitIntoOrderFulfillmentSystem(OrderDto orderDto) throws SystemException;

}