package com.melita.ordermanagement.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/*
 * @author sorokus.dev@gmail.com
 */

@Configuration
public class OrderingFulfilmentConfiguration {

    @Bean
    public RestTemplate ofRestTemplate() {
        return new RestTemplate();
    }

}
