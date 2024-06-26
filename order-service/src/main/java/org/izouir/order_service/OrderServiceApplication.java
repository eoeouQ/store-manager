package org.izouir.order_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Order Service API", version = "1.0"))
public class OrderServiceApplication {
    public static void main(final String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
