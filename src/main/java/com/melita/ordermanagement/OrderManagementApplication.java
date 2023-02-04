package com.melita.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/*
 * @author sorokus.dev@gmail.com
 */

@SpringBootApplication
@SecurityScheme(name = "swagger", scheme = "basic", type = SecuritySchemeType.HTTP)
public class OrderManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementApplication.class, args);
    }

}
