package org.izouir.order_service.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.izouir.order_service.dto.FiltersRequestDto;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/order")
@Tag(name = "Order service")
public interface OrderAPI {
    @Operation(summary = "Order placement")
    @PostMapping
    ResponseEntity<Void> place(@RequestBody final PlaceOrderRequestDto request);

    @Operation(summary = "Order decline")
    @DeleteMapping("/{orderId}")
    ResponseEntity<Void> decline(@PathVariable("orderId") Long orderId);

    @Operation(summary = "Order status update")
    @PutMapping("/{orderId}")
    ResponseEntity<Void> updateStatus(@PathVariable("orderId") final Long orderId, @RequestBody final String status);

    @Operation(summary = "Order history")
    @GetMapping("/history")
    ResponseEntity<List<OrderDto>> getOrderHistory();

    @Operation(summary = "Filtered orders")
    @PostMapping("/filter")
    ResponseEntity<List<OrderDto>> getOrdersFiltered(@RequestBody final FiltersRequestDto request);
}
