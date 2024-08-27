package org.izouir.order_service.service.impl;

import org.izouir.order_service.dto.ChangeAmountRequestDto;
import org.izouir.order_service.mapper.OrderPositionMapper;
import org.izouir.order_service.repository.OrderPositionRepository;
import org.izouir.order_service.repository.ProductRepository;
import org.izouir.order_service.repository.StoreRepository;
import org.izouir.store_manager_entities.entity.Order;
import org.izouir.store_manager_entities.entity.OrderPosition;
import org.izouir.store_manager_entities.entity.OrderPositionKey;
import org.izouir.store_manager_entities.entity.OrderStatus;
import org.izouir.store_manager_entities.entity.Product;
import org.izouir.store_manager_entities.entity.Store;
import org.izouir.store_manager_entities.entity.StoreLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.kafka.core.KafkaTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderPositionServiceImplTest {
    @InjectMocks
    private OrderPositionServiceImpl orderPositionService;

    @Mock
    private OrderPositionRepository orderPositionRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private KafkaTemplate<String, ChangeAmountRequestDto> kafkaTemplate;

    private Order order;
    private Product product;
    private Store store;

    @BeforeEach
    public void init() {
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
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(product));
        when(storeRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(store));
        when(orderPositionRepository.save(Mockito.any(OrderPosition.class)))
                .thenReturn(order.getPositions().get(0));
        when(kafkaTemplate.send(Mockito.anyString(), Mockito.any(ChangeAmountRequestDto.class)))
                .thenCallRealMethod();

        orderPositionService.place(order, OrderPositionMapper.toDtoList(order.getPositions()));

        verify(orderPositionRepository, times(1)).save(Mockito.any(OrderPosition.class));
    }

    @Test
    public void place_ShouldNotPlace() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(product));
        when(storeRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(store));
        when(orderPositionRepository.save(Mockito.any(OrderPosition.class)))
                .thenReturn(order.getPositions().get(0));

        orderPositionService.place(order, new ArrayList<>());

        verify(orderPositionRepository, times(0)).save(Mockito.any(OrderPosition.class));
    }
}
