package com.melita.ordermanagement.service;

/*
 * @author sorokus.dev@gmail.com
 */

import com.melita.ordermanagement.model.dto.OrderDto;

public interface EmailService {

    void sendHtmlMail(OrderDto orderDto);

}
