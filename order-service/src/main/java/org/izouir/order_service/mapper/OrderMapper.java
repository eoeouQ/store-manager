package org.izouir.order_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.shared_lib.entity.Order;

import java.util.List;

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

    public List<OrderDto> toDtoList(final List<Order> orderList) {
        return orderList.stream()
                .map(OrderMapper::toDto)
                .toList();
    }
}
