package org.izouir.order_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.order_service.dto.OrderDto;
import org.izouir.order_service.dto.OrderPositionDto;
import org.izouir.order_service.dto.PlaceOrderRequestDto;
import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderStatus;
import org.izouir.order_service.exception.OrderNotFoundException;
import org.izouir.order_service.mapper.OrderMapper;
import org.izouir.order_service.repository.OrderRepository;
import org.izouir.order_service.service.OrderPositionService;
import org.izouir.order_service.service.OrderService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderPositionService orderPositionService;

    private static final String ORDER_NOT_FOUND_MESSAGE = "Order with id = %s not found";
    private static final String ZERO_TOTAL_PRICE_MESSAGE = "Total price of the order must be greater than 0";

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

        orderPositionService.place(order, request.getPositions());

        return OrderMapper.toDto(order);
    }

    @Override
    @Transactional
    public void decline(final Long orderId) {
        final var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_MESSAGE, orderId)));
        orderPositionService.decline(order.getPositions());
        updateStatus(orderId, OrderStatus.STATUS_DECLINED.toString());
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

    private Integer calculateOrderTotalPrice(final List<OrderPositionDto> positions) {
        var totalPrice = 0;
        for (final var orderPositionDto : positions) {
            totalPrice += orderPositionDto.getQuantity();
        }
        return totalPrice;
    }
}
