package org.izouir.order_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.order_service.dto.ChangeAmountRequestDto;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderStatus;
import org.izouir.order_service.exception.OrderNotFoundException;
import org.izouir.order_service.exception.StoredProductNotFoundException;
import org.izouir.order_service.mapper.OrderMapper;
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
    private final StoredProductRepository storedProductRepository;
    private final WebClient inventoryWebClient;

    @Transactional
    public OrderDto place(final PlaceOrderRequestDto request) {
        inventoryWebClient.post()
                .uri("/subtract")
                .bodyValue(BodyInserters.fromValue(ChangeAmountRequestDto.builder()
                        .storedProductId(request.getStoredProductId())
                        .amount(request.getQuantity())
                        .build()))
                .retrieve();

        final var order = Order.builder()
                .status(OrderStatus.STATUS_CREATED)
                .storedProduct(storedProductRepository.findById(request.getStoredProductId()).orElseThrow(
                        () -> new StoredProductNotFoundException(
                                "Stored product with id = " + request.getStoredProductId() + " not found")))
                .quantity(request.getQuantity())
                .placedAt(Timestamp.from(Instant.now()))
                .build();
        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public OrderDto updateStatus(final Long orderId, final String status) {
        final var order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order with id = " + orderId + " not found"));
        order.setStatus(OrderStatus.valueOf(status));
        return OrderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public List<OrderDto> getOrderHistory() {
        return orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getPlacedAt))
                .map(OrderMapper::toDto)
                .toList();
    }
}
