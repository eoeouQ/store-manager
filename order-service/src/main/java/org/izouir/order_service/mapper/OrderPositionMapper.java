package org.izouir.order_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.order_service.dto.OrderPositionDto;
import org.izouir.shared_lib.entity.Order;
import org.izouir.shared_lib.entity.OrderPosition;
import org.izouir.shared_lib.entity.OrderPositionKey;
import org.izouir.shared_lib.entity.Product;
import org.izouir.shared_lib.entity.Store;

import java.util.List;

@UtilityClass
public class OrderPositionMapper {
    public OrderPositionDto toDto(final OrderPosition orderPosition) {
        return OrderPositionDto.builder()
                .productId(orderPosition.getId().getProduct().getId())
                .storeId(orderPosition.getId().getStore().getId())
                .quantity(orderPosition.getQuantity())
                .build();
    }

    public List<OrderPositionDto> toDtoList(final List<OrderPosition> orderPositionList) {
        return orderPositionList.stream()
                .map(OrderPositionMapper::toDto)
                .toList();
    }

    public static OrderPosition toEntity(final OrderPositionDto orderPositionDto,
                                         final Order order, final Product product, final Store store) {
        final var orderPositionKey = OrderPositionKey.builder()
                .order(order)
                .product(product)
                .store(store)
                .build();
        return OrderPosition.builder()
                .id(orderPositionKey)
                .quantity(orderPositionDto.getQuantity())
                .build();
    }
}
