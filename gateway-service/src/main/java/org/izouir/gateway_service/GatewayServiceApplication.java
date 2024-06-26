package org.izouir.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServiceApplication {
    public static void main(final String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
