package org.izouir.order_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Сервис заказов")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Размещение заказа")
    @PostMapping
    public OrderDto place(@RequestBody final PlaceOrderRequestDto request) {
        return orderService.place(request);
    }

    @Operation(summary = "Обновление статуса заказа")
    @PutMapping("/{orderId}")
    public OrderDto updateStatus(@PathVariable final Long orderId, @RequestBody final String status) {
        return orderService.updateStatus(orderId, status);
    }

    @Operation(summary = "История заказов")
    @GetMapping("/history")
    public List<OrderDto> getOrderHistory() {
        return orderService.getOrderHistory();
    }
}
