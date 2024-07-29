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
        final var orderPositionDto = OrderPositionMapper.toDto(orderPosition);

        assertEquals(orderPositionDto.getProductId(), orderPosition.getProduct().getId());
        assertEquals(orderPositionDto.getStoreId(), orderPosition.getStore().getId());
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
        assertEquals(mappedOrderPositionKey.getOrderId(), order.getId());
        assertEquals(mappedOrderPositionKey.getProductId(), product.getId());
        assertEquals(mappedOrderPositionKey.getStoreId(), store.getId());

        final var mappedOrderPositionOrder = mappedOrderPosition.getOrder();
        assertEquals(mappedOrderPositionOrder.getId(), order.getId());
        assertEquals(mappedOrderPositionOrder.getUserId(), order.getUserId());
        assertEquals(mappedOrderPositionOrder.getTotalPrice(), order.getTotalPrice());
        assertEquals(mappedOrderPositionOrder.getStatus(), order.getStatus());
        assertEquals(mappedOrderPositionOrder.getDate(), order.getDate());
        assertEquals(mappedOrderPositionOrder.getPositions(), order.getPositions());

        final var mappedOrderPositionProduct = mappedOrderPosition.getProduct();
        assertEquals(mappedOrderPositionProduct.getId(), product.getId());
        assertEquals(mappedOrderPositionProduct.getLabel(), product.getLabel());
        assertEquals(mappedOrderPositionProduct.getPrice(), product.getPrice());

        final var mappedOrderPositionStore = mappedOrderPosition.getStore();
        assertEquals(mappedOrderPositionStore.getId(), store.getId());
        assertEquals(mappedOrderPositionStore.getName(), store.getName());
        assertEquals(mappedOrderPositionStore.getLocation(), store.getLocation());
    }
}
