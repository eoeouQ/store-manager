package org.izouir.product_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Product Service API", version = "1.0"))
public class ProductServiceApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
