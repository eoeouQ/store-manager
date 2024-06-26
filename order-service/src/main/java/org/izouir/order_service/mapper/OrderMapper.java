package org.izouir.order_service.mapper;

import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.entity.Order;

public class OrderMapper {
    public static OrderDto toDto(final Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .storedProduct(StoredProductMapper.toDto(order.getStoredProduct()))
                .status(String.valueOf(order.getStatus()))
                .quantity(order.getQuantity())
                .placedAt(order.getPlacedAt())
                .build();
    }
}
