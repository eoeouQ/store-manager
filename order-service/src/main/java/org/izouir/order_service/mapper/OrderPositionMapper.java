package org.izouir.order_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.order_service.dto.OrderPositionDto;
import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderPosition;
import org.izouir.order_service.entity.OrderPositionKey;
import org.izouir.order_service.entity.Product;
import org.izouir.order_service.entity.Store;

import java.util.List;

@UtilityClass
public class OrderPositionMapper {
    public OrderPositionDto toDto(final OrderPosition orderPosition) {
        return OrderPositionDto.builder()
                .productId(orderPosition.getProduct().getId())
                .storeId(orderPosition.getStore().getId())
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
                .orderId(order.getId())
                .productId(product.getId())
                .storeId(store.getId())
                .build();
        return OrderPosition.builder()
                .id(orderPositionKey)
                .order(order)
                .product(product)
                .store(store)
                .quantity(orderPositionDto.getQuantity())
                .build();
    }
}
