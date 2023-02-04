package com.melita.ordermanagement.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/*
 * @author sorokus.dev@gmail.com
 */

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI orderManagementOpenAPI() {
        return new OpenAPI().info(new Info().title("Melita OrderManagement API")
                                            .description("Melita OrderManagement API Application")
                                            .version("v0.0.1")
                                            .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
