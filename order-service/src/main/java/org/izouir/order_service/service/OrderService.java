package org.izouir.order_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.order_service.dto.ChangeAmountRequestDto;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderPosition;
import org.izouir.order_service.entity.OrderStatus;
import org.izouir.order_service.exception.OrderNotFoundException;
import org.izouir.order_service.exception.StoredProductNotFoundException;
import org.izouir.order_service.mapper.OrderMapper;
import org.izouir.order_service.repository.OrderPositionRepository;
import org.izouir.order_service.repository.OrderRepository;
import org.izouir.order_service.repository.StoredProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderPositionRepository orderPositionRepository;
    private final StoredProductRepository storedProductRepository;
    private final WebClient inventoryWebClient;

    private static final String ORDER_NOT_FOUND_MESSAGE = "Order with id = %s not found";
    private static final String STORED_PRODUCT_NOT_FOUND_MESSAGE = "Stored product with id = %s not found";

    @Transactional
    public OrderDto place(final PlaceOrderRequestDto request) {
        var totalPrice = 0;
        for (final var orderPositionDto : request.getPositions()) {
            inventoryWebClient.post()
                    .uri("/subtract")
                    .bodyValue(BodyInserters.fromValue(ChangeAmountRequestDto.builder()
                            .storedProductId(orderPositionDto.getStoredProduct().getId())
                            .amount(orderPositionDto.getQuantity())
                            .build()))
                    .retrieve();

            totalPrice += orderPositionDto.getQuantity();
        }

        var order = Order.builder()
                .userId(request.getUserId())
                .totalPrice(totalPrice)
                .status(OrderStatus.STATUS_CREATED)
                .date(Timestamp.from(Instant.now()))
                .build();
        order = orderRepository.save(order);
        final var orderId = order.getId();

        for (final var orderPositionDto : request.getPositions()) {
            final var storedProductId = orderPositionDto.getStoredProduct().getId();
            final var orderPosition = OrderPosition.builder()
                    .storedProduct(storedProductRepository.findById(storedProductId)
                            .orElseThrow(() -> new StoredProductNotFoundException(
                                    String.format(STORED_PRODUCT_NOT_FOUND_MESSAGE, storedProductId))))
                    .order(order)
                    .quantity(orderPositionDto.getQuantity())
                    .build();
            orderPositionRepository.save(orderPosition);
        }
        return OrderMapper.toDto(orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, orderId))));
    }

    @Transactional
    public OrderDto updateStatus(final Long orderId, final String status) {
        final var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, orderId)));
        order.setStatus(OrderStatus.valueOf(status));
        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public List<OrderDto> getOrderHistory() {
        return orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getDate))
                .map(OrderMapper::toDto)
                .toList();
    }
}
