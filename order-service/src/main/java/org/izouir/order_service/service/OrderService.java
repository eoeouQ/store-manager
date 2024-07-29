package org.izouir.order_service.service;

import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;

import java.util.List;

public interface OrderService {
    OrderDto place(final PlaceOrderRequestDto request);

    OrderDto updateStatus(final Long orderId, final String status);

    List<OrderDto> getOrderHistory();
}
