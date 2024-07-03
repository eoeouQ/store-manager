package org.izouir.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order service")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Order placement")
    @PostMapping
    public ResponseEntity<OrderDto> place(@RequestBody final PlaceOrderRequestDto request) {
        return new ResponseEntity<>(orderService.place(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Order status update")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable final Long orderId, @RequestBody final String status) {
        return new ResponseEntity<>(orderService.updateStatus(orderId, status), HttpStatus.OK);
    }

    @Operation(summary = "Order history")
    @GetMapping("/history")
    public ResponseEntity<List<OrderDto>> getOrderHistory() {
        return new ResponseEntity<>(orderService.getOrderHistory(), HttpStatus.OK);
    }
}
