package org.izouir.order_service.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/orders")
@Tag(name = "Order service")
public interface OrderAPI {
    @Operation(summary = "Order placement")
    @PostMapping
    ResponseEntity<OrderDto> place(@RequestBody final PlaceOrderRequestDto request);

    @Operation(summary = "Order status update")
    @PutMapping("/{orderId}")
    ResponseEntity<OrderDto> updateStatus(@PathVariable final Long orderId, @RequestBody final String status);

    @Operation(summary = "Order history")
    @GetMapping("/history")
    ResponseEntity<List<OrderDto>> getOrderHistory();
}
