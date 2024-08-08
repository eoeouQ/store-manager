package org.izouir.order_service.service;

import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;

import java.util.List;

public interface OrderService {
    void place(final PlaceOrderRequestDto request);

    void decline(Long orderId);

    void updateStatus(final Long orderId, final String status);

    List<OrderDto> getOrderHistory();
}
