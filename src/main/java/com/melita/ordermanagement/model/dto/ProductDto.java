package com.melita.ordermanagement.model.dto;

import java.util.List;

import lombok.Data;

/*
 * @author sorokus.dev@gmail.com
 */

@Data
public class ProductDto {
    private Long             id;
    private String           name;
    private String           description;
    private List<PackageDto> packages;
}

