package com.bigdata.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("VIEW#BIGDATA")
                        .description("This is the backend application for the VIEW#BIGDATA project.")
                        .version("v1.0"));
    }

}
