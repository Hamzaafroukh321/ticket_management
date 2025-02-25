package com.example.ticket_management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Ticket Management API",
                version = "1.0",
                description = "API pour gérer les tickets IT"
        )
)
public class SwaggerConfig {

}
