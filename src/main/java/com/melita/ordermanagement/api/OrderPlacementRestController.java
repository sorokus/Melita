package com.melita.ordermanagement.api;

import com.melita.ordermanagement.model.convertor.ProductConvertor;
import com.melita.ordermanagement.model.dto.OrderDto;
import com.melita.ordermanagement.model.dto.ProductDto;
import com.melita.ordermanagement.service.OrderPlacementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private OrderPlacementService orderPlacementService;

    @Autowired
    private ProductConvertor productConvertor;

    @GetMapping(value = {"/available_products_with_packages"})
    public List<ProductDto> getAvailableProductsWithPackages() {
        return orderPlacementService.getAvailableProductsWithPackages()
                                    .stream()
                                    .map(productConvertor::convertToDto)
                                    .toList();
    }

    @PostMapping(value = "/placeOrder")
    public void placeOrder(@Valid @RequestBody OrderDto orderDto) {
        orderPlacementService.placeOrder(orderDto);
    }

}