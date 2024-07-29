package org.izouir.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.izouir.order_service.controller.api.OrderAPI;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderAPI {
    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderDto> place(@RequestBody final PlaceOrderRequestDto request) {
        return new ResponseEntity<>(orderService.place(request), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderDto> updateStatus(@PathVariable final Long orderId, @RequestBody final String status) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrderHistory() {
        return ResponseEntity.ok(orderService.getOrderHistory());
    }
}
