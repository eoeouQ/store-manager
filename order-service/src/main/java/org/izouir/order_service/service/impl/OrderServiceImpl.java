package org.izouir.order_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.order_service.dto.ChangeAmountRequestDto;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.OrderPositionDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderStatus;
import org.izouir.order_service.exception.OrderNotFoundException;
import org.izouir.order_service.exception.ProductNotFoundException;
import org.izouir.order_service.exception.StoreNotFoundException;
import org.izouir.order_service.mapper.OrderMapper;
import org.izouir.order_service.mapper.OrderPositionMapper;
import org.izouir.order_service.repository.OrderPositionRepository;
import org.izouir.order_service.repository.OrderRepository;
import org.izouir.order_service.repository.ProductRepository;
import org.izouir.order_service.repository.StoreRepository;
import org.izouir.order_service.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final OrderPositionRepository orderPositionRepository;
    private final WebClient inventoryWebClient;

    private static final String SUBTRACT_PRODUCTS_URI = "/subtract";
    private static final String ZERO_TOTAL_PRICE_MESSAGE = "Total price of the order must be greater than 0";
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order with id = %s not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id = %s not found";
    private static final String STORE_NOT_FOUND_MESSAGE = "Store with id = %s not found";

    @Override
    @Transactional
    public OrderDto place(final PlaceOrderRequestDto request) {
        final var totalPrice = calculateOrderTotalPrice(request.getPositions());
        if (totalPrice <= 0) {
            throw new IllegalArgumentException(ZERO_TOTAL_PRICE_MESSAGE);
        }
        var order = Order.builder()
                .userId(request.getUserId())
                .totalPrice(totalPrice)
                .status(OrderStatus.STATUS_CREATED)
                .date(Timestamp.from(Instant.now()))
                .build();
        order = orderRepository.save(order);

        placeOrderPositions(order, request.getPositions());

        return OrderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto updateStatus(final Long orderId, final String status) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, orderId)));
        order.setStatus(OrderStatus.valueOf(status));
        order = orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrderHistory() {
        final var orderHistory = orderRepository.findAllByOrderByDateAsc();
        return OrderMapper.toDtoList(orderHistory);
    }

    private void placeOrderPositions(final Order order, final List<OrderPositionDto> positions) {
        for (final var orderPositionDto : positions) {
            final var productId = orderPositionDto.getProductId();
            final var storeId = orderPositionDto.getStoreId();

            inventoryWebClient.post()
                    .uri(SUBTRACT_PRODUCTS_URI)
                    .bodyValue(BodyInserters.fromValue(ChangeAmountRequestDto.builder()
                            .productId(productId)
                            .storeId(storeId)
                            .amount(orderPositionDto.getQuantity())
                            .build()))
                    .retrieve();

            final var product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));
            final var store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new StoreNotFoundException(String.format(STORE_NOT_FOUND_MESSAGE, storeId)));
            final var orderPosition = OrderPositionMapper.toEntity(orderPositionDto, order, product, store);
            orderPositionRepository.save(orderPosition);
        }
    }

    private Integer calculateOrderTotalPrice(final List<OrderPositionDto> positions) {
        var totalPrice = 0;
        for (final var orderPositionDto : positions) {
            totalPrice += orderPositionDto.getQuantity();
        }
        return totalPrice;
    }
}
