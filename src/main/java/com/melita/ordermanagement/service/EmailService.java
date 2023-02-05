package com.melita.ordermanagement.service;

/*
 * @author sorokus.dev@gmail.com
 */

import com.melita.ordermanagement.base.exceptions.SystemException;
import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.model.dto.ProductDto;

import java.util.Collection;

public interface EmailService {

    void sendNewOrderForApprovalHtmlMail(Long orderId,
                                         OrderDto orderDto,
                                         Collection<ProductDto> selectedProducts,
                                         Collection<ProductDto> approvalProducts,
                                         String linkForApproval) throws SystemException;

    void sendNewOrderHtmlMail(Long orderId,
                              OrderDto orderDto,
                              Collection<ProductDto> selectedProducts) throws SystemException;

}
