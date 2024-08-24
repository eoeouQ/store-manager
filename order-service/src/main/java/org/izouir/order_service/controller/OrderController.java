package org.izouir.order_service.controller;

import lombok.RequiredArgsConstructor;
import org.izouir.order_service.controller.api.OrderAPI;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderAPI {
    private final OrderService orderService;

    @Override
    public ResponseEntity<Void> place(final PlaceOrderRequestDto request) {
        orderService.place(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> decline(final Long orderId) {
        orderService.decline(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateStatus(final Long orderId, final String status) {
        orderService.updateStatus(orderId, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrderHistory() {
        return ResponseEntity.ok(orderService.getOrderHistory());
    }

    @Override
    public ResponseEntity<List<OrderDto>> getOrdersFiltered(final String userId,
                                                            final String totalPrice,
                                                            final String status,
                                                            final String date) {
        return ResponseEntity.ok(orderService.getOrdersFiltered(userId, totalPrice, status, date));
    }
}
