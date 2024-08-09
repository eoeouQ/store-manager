package org.izouir.inventory_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.entity.InventoryOperation;
import org.izouir.inventory_service.entity.StoredProduct;
import org.izouir.inventory_service.entity.StoredProductKey;
import org.izouir.inventory_service.exception.ProductNotFoundException;
import org.izouir.inventory_service.exception.StoreNotFoundException;
import org.izouir.inventory_service.exception.StoredProductNotFoundException;
import org.izouir.inventory_service.repository.ProductRepository;
import org.izouir.inventory_service.repository.StoreRepository;
import org.izouir.inventory_service.repository.StoredProductRepository;
import org.izouir.inventory_service.service.StoredProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoredProductServiceImpl implements StoredProductService {
    private final StoredProductRepository storedProductRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    private static final String STORED_PRODUCT_NOT_FOUND_MESSAGE = "Stored product with productId %s and storeId %s not found";
    private static final String EXCEEDING_CHANGE_AMOUNT_MESSAGE = "Change amount exceeds the stored one";

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id = %s not found";
    private static final String STORE_NOT_FOUND_MESSAGE = "Store with id = %s not found";

    @KafkaListener(topics = "${spring.kafka.consumer.properties.topic-add}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "requestListener")
    void listenToAdd(final ChangeAmountRequestDto request) {
        changeAmount(request, InventoryOperation.OPERATION_ADD);
    }

    @KafkaListener(topics = "${spring.kafka.consumer.properties.topic-subtract}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "requestListener")
    void listenToSubtract(final ChangeAmountRequestDto request) {
        changeAmount(request, InventoryOperation.OPERATION_SUBTRACT);
    }

    @Override
    public void changeAmount(final ChangeAmountRequestDto request, final InventoryOperation operation) {
        final var storedProduct = constructFrom(request);

        switch (operation) {
            case OPERATION_ADD -> storedProduct.setQuantity(storedProduct.getQuantity() + request.getAmount());
            case OPERATION_SUBTRACT -> {
                if (storedProduct.getQuantity() - request.getAmount() < 0) {
                    throw new IllegalArgumentException(EXCEEDING_CHANGE_AMOUNT_MESSAGE);
                }
                storedProduct.setQuantity(storedProduct.getQuantity() - request.getAmount());
            }
        }

        storedProductRepository.save(storedProduct);
    }

    private StoredProduct constructFrom(final ChangeAmountRequestDto request) {
        final var productId = request.getProductId();
        final var storeId = request.getStoreId();
        final var product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));
        final var store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(String.format(STORE_NOT_FOUND_MESSAGE, storeId)));
        final var storedProductId = StoredProductKey.builder()
                .product(product)
                .store(store)
                .build();
        return storedProductRepository.findById(storedProductId)
                .orElseThrow(() -> new StoredProductNotFoundException(
                        String.format(STORED_PRODUCT_NOT_FOUND_MESSAGE, productId, storeId)));
    }
}
