package org.izouir.order_service.mapper;

import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderPosition;
import org.izouir.order_service.entity.OrderPositionKey;
import org.izouir.order_service.entity.OrderStatus;
import org.izouir.order_service.entity.Product;
import org.izouir.order_service.entity.Store;
import org.izouir.order_service.entity.StoreLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderMapperTest {
    private static Order order;

    @BeforeAll
    public static void setUp() {
        order = Order.builder()
                .id(1L)
                .userId(1L)
                .totalPrice(10)
                .status(OrderStatus.STATUS_CREATED)
                .date(Timestamp.from(Instant.now()))
                .build();
        final var product = Product.builder()
                .id(1L)
                .label("TEST")
                .price(1)
                .build();
        final var store = Store.builder()
                .id(1L)
                .name("TEST")
                .location(StoreLocation.LOCATION_BELARUS)
                .build();
        final var orderPosition = OrderPosition.builder()
                .id(OrderPositionKey.builder()
                        .orderId(1L)
                        .productId(1L)
                        .storeId(1L)
                        .build())
                .order(order)
                .product(product)
                .store(store)
                .quantity(10)
                .build();
        order.setPositions(List.of(orderPosition));
    }

    @Test
    public void toDto_ShouldPass() {
        final var orderDto = OrderMapper.toDto(order);

        assertNotNull(orderDto);
        assertEquals(orderDto.getId(), order.getId());
        assertEquals(orderDto.getUserId(), order.getUserId());
        assertEquals(orderDto.getTotalPrice(), order.getTotalPrice());
        assertEquals(orderDto.getStatus(), order.getStatus().toString());
        assertEquals(orderDto.getDate(), order.getDate());
        assertEquals(orderDto.getPositions(), OrderPositionMapper.toDtoList(order.getPositions()));
    }

    @Test
    public void toDtoList_ShouldPass() {
        assertEquals(0, OrderMapper.toDtoList(new ArrayList<>()).size());
        assertEquals(1, OrderMapper.toDtoList(List.of(order)).size());
    }
}
