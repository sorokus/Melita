package com.melita.ordermanagement.api;

import com.melita.ordermanagement.model.convertor.OrderConvertor;
import com.melita.ordermanagement.model.convertor.ProductConvertor;
import com.melita.ordermanagement.model.dto.OrderApproveDto;
import com.melita.ordermanagement.service.OrderPlacementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * @author sorokus.dev@gmail.com
 */

@RestController
@RequestMapping("/api/v1/ordermanagement/approval")
@SecurityRequirement(name = "swagger")
public class OrderApprovalRestController {

    @Autowired
    private OrderPlacementService orderPlacementService;

    @Autowired
    private OrderConvertor orderConvertor;

    @Autowired
    private ProductConvertor productConvertor;

    @PostMapping(value = "/approveOrder")
    public void approveOrder(@RequestBody OrderApproveDto orderApproveDto) {
    }

}