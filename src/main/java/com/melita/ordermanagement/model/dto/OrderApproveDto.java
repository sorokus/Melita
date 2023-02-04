package com.melita.ordermanagement.model.dto;

import java.util.Date;

import lombok.Data;

/*
 * @author sorokus.dev@gmail.com
 */

@Data
public class OrderApproveDto {
    private Long    orderId;
    private boolean isApproved; // Could have more states, but we just have 2: approved/rejected for simplicity
    private String  resolution;
    private Date    decissionDate = new Date();
    private String  approver; // Login of the agent making decision
}
