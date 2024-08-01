package org.izouir.order_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.order_service.dto.ChangeAmountRequestDto;
import org.izouir.order_service.dto.OrderPositionDto;
import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderPosition;
import org.izouir.order_service.exception.ProductNotFoundException;
import org.izouir.order_service.exception.StoreNotFoundException;
import org.izouir.order_service.mapper.OrderPositionMapper;
import org.izouir.order_service.repository.OrderPositionRepository;
import org.izouir.order_service.repository.ProductRepository;
import org.izouir.order_service.repository.StoreRepository;
import org.izouir.order_service.service.OrderPositionService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderPositionServiceImpl implements OrderPositionService {
    private final OrderPositionRepository orderPositionRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final WebClient inventoryWebClient;

    private static final String ADD_PRODUCTS_URI = "/add";
    private static final String SUBTRACT_PRODUCTS_URI = "/subtract";

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id = %s not found";
    private static final String STORE_NOT_FOUND_MESSAGE = "Store with id = %s not found";

    @Override
    @Transactional
    public void place(final Order order, final List<OrderPositionDto> orderPositionDtoList) {
        for (final var orderPositionDto : orderPositionDtoList) {
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

    @Override
    @Transactional
    public void decline(final List<OrderPosition> orderPositions) {
        for (final var orderPosition : orderPositions) {
            inventoryWebClient.post()
                    .uri(ADD_PRODUCTS_URI)
                    .bodyValue(BodyInserters.fromValue(ChangeAmountRequestDto.builder()
                            .productId(orderPosition.getProduct().getId())
                            .storeId(orderPosition.getStore().getId())
                            .amount(orderPosition.getQuantity())))
                    .retrieve();
        }
    }
}
