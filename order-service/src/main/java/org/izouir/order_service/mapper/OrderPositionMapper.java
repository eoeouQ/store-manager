package org.izouir.order_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.order_service.dto.OrderPositionDto;
import org.izouir.order_service.entity.OrderPosition;

import java.util.List;

@UtilityClass
public class OrderPositionMapper {
    public OrderPositionDto toDto(final OrderPosition orderPosition) {
        return OrderPositionDto.builder()
                .storedProduct(StoredProductMapper.toDto(orderPosition.getStoredProduct()))
                .quantity(orderPosition.getQuantity())
                .build();
    }

    public List<OrderPositionDto> toDtoList(final List<OrderPosition> orderPositionList) {
        return orderPositionList.stream()
                .map(OrderPositionMapper::toDto)
                .toList();
    }
}
