package org.izouir.order_service.service.impl;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.http.HttpHeaders;
import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderPosition;
import org.izouir.order_service.entity.OrderPositionKey;
import org.izouir.order_service.entity.OrderStatus;
import org.izouir.order_service.entity.Product;
import org.izouir.order_service.entity.Store;
import org.izouir.order_service.entity.StoreLocation;
import org.izouir.order_service.mapper.OrderPositionMapper;
import org.izouir.order_service.repository.OrderPositionRepository;
import org.izouir.order_service.repository.ProductRepository;
import org.izouir.order_service.repository.StoreRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
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

    @Spy
    private static WebClient inventoryWebClient;
    private static MockWebServer mockWebServer;

    private Order order;
    private Product product;
    private Store store;

    @BeforeAll
    public static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        inventoryWebClient = WebClient.create(mockWebServer.url("/").toString());
    }

    @AfterAll
    public static void tearDown() throws IOException {
        mockWebServer.close();
    }

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

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("")
        );
    }

    @Test
    public void place_ShouldPlace() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(product));
        when(storeRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(store));
        when(orderPositionRepository.save(Mockito.any(OrderPosition.class)))
                .thenReturn(order.getPositions().get(0));

        orderPositionService.place(order, OrderPositionMapper.toDtoList(order.getPositions()));

        verify(orderPositionRepository, times(1)).save(Mockito.any(OrderPosition.class));
        verify(inventoryWebClient, times(1)).post();
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
        verify(inventoryWebClient, times(0)).post();
    }

    @Test
    public void decline_ShouldDecline() {
        orderPositionService.decline(order.getPositions());

        verify(inventoryWebClient, times(1)).post();
    }

    @Test
    public void decline_ShouldNotDecline() {
        orderPositionService.decline(new ArrayList<>());

        verify(inventoryWebClient, times(0)).post();
    }
}
