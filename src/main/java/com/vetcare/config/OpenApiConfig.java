package com.vetcare.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VetCare API")
                        .version("1.0.0")
                        .description("""
                                REST API for the comprehensive management of veterinary clinics.
                                
                                ## Key features:
                                - Management of owners and pets
                                - Administration of veterinarians and specialties
                                - Management of clinics and branches
                                - Recording of medical visits
                                - Complete medical history
                                
                                ## Technologies:
                                - Spring Boot 3.3.4
                                - Java 21
                                - MySQL 8.0
                                - JPA/Hibernate
                                """)
                        .contact(new Contact()
                                .name("VetCare Team")
                                .email("g.huaman.ot@gmail.com")
                                .url("https://guillermo-huaman-dev.netlify.app/en/"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.vetcare.com")
                                .description("Production Server")
                ));
    }

}
