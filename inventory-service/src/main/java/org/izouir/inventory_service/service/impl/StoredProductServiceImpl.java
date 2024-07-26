package org.izouir.inventory_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.dto.StoredProductDto;
import org.izouir.inventory_service.entity.StoredProductKey;
import org.izouir.inventory_service.exception.StoredProductNotFoundException;
import org.izouir.inventory_service.mapper.StoredProductMapper;
import org.izouir.inventory_service.repository.StoredProductRepository;
import org.izouir.inventory_service.service.StoredProductService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoredProductServiceImpl implements StoredProductService {
    private final StoredProductRepository storedProductRepository;

    private static final String STORED_PRODUCT_NOT_FOUND_MESSAGE = "Stored product with productId %s and storeId %s not found";
    private static final String EXCEEDING_CHANGE_AMOUNT_MESSAGE = "Change amount exceeds the stored one";

    @Override
    @Transactional
    public StoredProductDto addAmount(final ChangeAmountRequestDto request) {
        final var storedProductId = StoredProductKey.builder()
                .productId(request.getProductId())
                .storeId(request.getStoreId())
                .build();
        final var storedProduct = storedProductRepository.findById(storedProductId)
                .orElseThrow(() -> new StoredProductNotFoundException(
                        String.format(STORED_PRODUCT_NOT_FOUND_MESSAGE, storedProductId.getProductId(), storedProductId.getStoreId())));
        storedProduct.setQuantity(storedProduct.getQuantity() + request.getAmount());
        final var updatedStoredProduct = storedProductRepository.save(storedProduct);
        return StoredProductMapper.toDto(updatedStoredProduct);
    }

    @Override
    @Transactional
    public StoredProductDto subtractAmount(final ChangeAmountRequestDto request) {
        final var storedProductId = StoredProductKey.builder()
                .productId(request.getProductId())
                .storeId(request.getStoreId())
                .build();
        final var storedProduct = storedProductRepository.findById(storedProductId)
                .orElseThrow(() -> new StoredProductNotFoundException(
                        String.format(STORED_PRODUCT_NOT_FOUND_MESSAGE, storedProductId.getProductId(), storedProductId.getStoreId())));
        if (storedProduct.getQuantity() - request.getAmount() < 0) {
            throw new IllegalArgumentException(EXCEEDING_CHANGE_AMOUNT_MESSAGE);
        }
        storedProduct.setQuantity(storedProduct.getQuantity() - request.getAmount());
        final var updatedStoredProduct = storedProductRepository.save(storedProduct);
        return StoredProductMapper.toDto(updatedStoredProduct);
    }
}
