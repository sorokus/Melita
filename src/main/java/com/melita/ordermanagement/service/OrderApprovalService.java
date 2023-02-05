package com.melita.ordermanagement.service;

/*
 * @author sorokus.dev@gmail.com
 */

import com.melita.ordermanagement.base.exceptions.BusinessException;
import com.melita.ordermanagement.base.exceptions.SystemException;

public interface OrderApprovalService {

    void approveOrder(Long orderId, String approverName) throws BusinessException, SystemException;

}
