package org.izouir.order_service.mapper;

import org.izouir.order_service.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPositionMapperTest {
    private static Order order;
    private static Product product;
    private static Store store;
    private static OrderPosition orderPosition;

    @BeforeAll
    public static void setUp() {
        order = Order.builder()
                .id(1L)
                .userId(1L)
                .totalPrice(10)
                .status(OrderStatus.STATUS_CREATED)
                .date(Timestamp.from(Instant.now()))
                .build();
        product = Product.builder()
                .id(1L)
                .label("TEST")
                .price(1)
                .build();
        store = Store.builder()
                .id(1L)
                .name("TEST")
                .location(StoreLocation.LOCATION_BELARUS)
                .build();
        orderPosition = OrderPosition.builder()
                .id(OrderPositionKey.builder()
                        .order(order)
                        .product(product)
                        .store(store)
                        .build())
                .quantity(10)
                .build();
        order.setPositions(List.of(orderPosition));
    }

    @Test
    public void toDto_ShouldPass() {
        final var orderPositionDto = OrderPositionMapper.toDto(orderPosition);

        assertEquals(orderPositionDto.getProductId(), orderPosition.getId().getProduct().getId());
        assertEquals(orderPositionDto.getStoreId(), orderPosition.getId().getStore().getId());
        assertEquals(orderPositionDto.getQuantity(), orderPosition.getQuantity());
    }

    @Test
    public void toDtoList_ShouldPass() {
        assertEquals(0, OrderPositionMapper.toDtoList(new ArrayList<>()).size());
        assertEquals(1, OrderPositionMapper.toDtoList(List.of(orderPosition)).size());
    }

    @Test
    public void toEntity_ShouldPass() {
        final var orderPositionDto = OrderPositionMapper.toDto(orderPosition);
        final var mappedOrderPosition = OrderPositionMapper.toEntity(orderPositionDto, order, product, store);

        final var mappedOrderPositionKey = mappedOrderPosition.getId();
        assertEquals(mappedOrderPositionKey.getOrder().getId(), order.getId());
        assertEquals(mappedOrderPositionKey.getProduct().getId(), product.getId());
        assertEquals(mappedOrderPositionKey.getStore().getId(), store.getId());

        final var mappedOrderPositionOrder = mappedOrderPosition.getId().getOrder();
        assertEquals(mappedOrderPositionOrder.getId(), order.getId());
        assertEquals(mappedOrderPositionOrder.getUserId(), order.getUserId());
        assertEquals(mappedOrderPositionOrder.getTotalPrice(), order.getTotalPrice());
        assertEquals(mappedOrderPositionOrder.getStatus(), order.getStatus());
        assertEquals(mappedOrderPositionOrder.getDate(), order.getDate());
        assertEquals(mappedOrderPositionOrder.getPositions(), order.getPositions());

        final var mappedOrderPositionProduct = mappedOrderPosition.getId().getProduct();
        assertEquals(mappedOrderPositionProduct.getId(), product.getId());
        assertEquals(mappedOrderPositionProduct.getLabel(), product.getLabel());
        assertEquals(mappedOrderPositionProduct.getPrice(), product.getPrice());

        final var mappedOrderPositionStore = mappedOrderPosition.getId().getStore();
        assertEquals(mappedOrderPositionStore.getId(), store.getId());
        assertEquals(mappedOrderPositionStore.getName(), store.getName());
        assertEquals(mappedOrderPositionStore.getLocation(), store.getLocation());
    }
}
