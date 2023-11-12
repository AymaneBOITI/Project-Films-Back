package com.projectweb.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Add the logging interceptor to the RestTemplate instance
        restTemplate.setInterceptors(Collections.singletonList(new LoggingRequestInterceptor()));

        return restTemplate;
    }
}
