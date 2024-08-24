package org.izouir.order_service.service.impl;

import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderPosition;
import org.izouir.order_service.entity.OrderPositionKey;
import org.izouir.order_service.entity.OrderStatus;
import org.izouir.order_service.entity.Product;
import org.izouir.order_service.entity.Store;
import org.izouir.order_service.entity.StoreLocation;
import org.izouir.order_service.exception.OrderNotFoundException;
import org.izouir.order_service.mapper.OrderPositionMapper;
import org.izouir.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderPositionServiceImpl orderPositionService;

    private Order order;

    @BeforeEach
    public void init() {
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
                        .order(order)
                        .product(product)
                        .store(store)
                        .build())
                .quantity(10)
                .build();
        order.setPositions(List.of(orderPosition));
    }

    @Test
    public void place_ShouldPlace() {
        final var placeOrderRequestDto = PlaceOrderRequestDto.builder()
                .userId(1L)
                .positions(OrderPositionMapper.toDtoList(order.getPositions()))
                .build();
        when(orderRepository.save(Mockito.any(Order.class)))
                .thenReturn(order);

        orderService.place(placeOrderRequestDto);

        verify(orderRepository, times(1)).save(Mockito.any(Order.class));
        verify(orderPositionService, times(1)).place(Mockito.any(Order.class), Mockito.any());
    }

    @Test
    public void place_ShouldNotPlace() {
        final var placeOrderRequestDto = PlaceOrderRequestDto.builder()
                .userId(1L)
                .positions(new ArrayList<>())
                .build();

        assertThrows(IllegalArgumentException.class, () -> orderService.place(placeOrderRequestDto));
        verify(orderRepository, times(0)).save(Mockito.any(Order.class));
    }

    @Test
    public void updateStatus_ShouldUpdate() {
        when(orderRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(order));
        order.setStatus(OrderStatus.STATUS_DELIVERING);
        when(orderRepository.save(Mockito.any(Order.class)))
                .thenReturn(order);

        orderService.updateStatus(1L, OrderStatus.STATUS_DELIVERING.toString());

        verify(orderRepository, times(1)).findById(Mockito.any(Long.class));
        verify(orderRepository, times(1)).save(Mockito.any(Order.class));
    }

    @Test
    public void updateStatus_ShouldNotUpdate() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));
        when(orderRepository.save(Mockito.any(Order.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(OrderNotFoundException.class,
                () -> orderService.updateStatus(-1L, OrderStatus.STATUS_CREATED.toString()));
        assertThrows(IllegalArgumentException.class,
                () -> orderService.updateStatus(1L, "BAD_STATUS"));
        verify(orderRepository, times(2)).findById(Mockito.any(Long.class));
        verify(orderRepository, times(0)).save(Mockito.any(Order.class));
    }

    @Test
    public void getOrderHistory_ShouldFind() {
        when(orderRepository.findAllByOrderByDateAsc())
                .thenReturn(List.of(order));

        final var orderHistoryDtoList = orderService.getOrderHistory();

        assertNotNull(orderHistoryDtoList);
        assertEquals(1, orderHistoryDtoList.size());
        verify(orderRepository, times(1)).findAllByOrderByDateAsc();
    }

    @Test
    public void getOrderHistory_ShouldNotFound() {
        when(orderRepository.findAllByOrderByDateAsc())
                .thenReturn(new ArrayList<>());

        final var orderHistoryDtoList = orderService.getOrderHistory();

        assertNotNull(orderHistoryDtoList);
        assertEquals(0, orderHistoryDtoList.size());
        verify(orderRepository, times(1)).findAllByOrderByDateAsc();
    }
}
