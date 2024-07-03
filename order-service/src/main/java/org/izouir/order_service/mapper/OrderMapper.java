package org.izouir.order_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.entity.Order;

@UtilityClass
public class OrderMapper {
    public OrderDto toDto(final Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalPrice(order.getTotalPrice())
                .status(String.valueOf(order.getStatus()))
                .date(order.getDate())
                .positions(OrderPositionMapper.toDtoList(order.getPositions()))
                .build();
    }
}
