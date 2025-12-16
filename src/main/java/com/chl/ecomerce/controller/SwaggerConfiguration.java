package com.chl.ecomerce.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("E-commerce")
                .version("1.0.0")
                .description("API backend desenvolvida com Spring Boot para estudo e prática de um sistema de e-commerce no contexto de um supermercado. " +
                        "Permite o cadastro e a consulta de usuarios, pedidos e produtos, com foco em persistência de dados e mapeamento de relacionamentos " +
                        "utilizando Spring Data JPA."
                )
        );
    }
}