package com.melita.ordermanagement.api;

import com.melita.ordermanagement.base.exceptions.SystemException;
import com.melita.ordermanagement.model.convertor.ProductConvertor;
import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.model.dto.ProductDto;
import com.melita.ordermanagement.service.OrderPlacementService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

/**
 * @author sorokus.dev@gmail.com
 */

@RestController
@RequestMapping("/api/v1/ordermanagement/placement")
@SecurityRequirement(name = "swagger")
public class OrderPlacementRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderPlacementRestController.class);

    private OrderPlacementService orderPlacementService;
    private ProductConvertor      productConvertor;

    @GetMapping(value = {"/available_products_with_packages"})
    public List<ProductDto> getAvailableProductsWithPackages(Principal principal) {
        LOGGER.debug("A request to get available products with packages is arrived from '" + principal.getName()+"'");
        return orderPlacementService.getAvailableProductsWithPackages()
                                    .stream()
                                    .map(productConvertor::convertToDto)
                                    .toList();
    }

    @PostMapping(value = "/placeOrder")
    public void placeOrder(@Valid @RequestBody OrderDto orderDto, Principal principal) throws SystemException {
        LOGGER.debug("A request to place an Order is arrived from '" + principal.getName()+"'");
        orderPlacementService.placeOrder(orderDto);
    }

    @Autowired
    public void setOrderPlacementService(OrderPlacementService orderPlacementService) {
        this.orderPlacementService = orderPlacementService;
    }

    @Autowired
    public void setProductConvertor(ProductConvertor productConvertor) {
        this.productConvertor = productConvertor;
    }

}