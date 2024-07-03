package org.izouir.inventory_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.dto.StoredProductDto;
import org.izouir.inventory_service.exception.StoredProductNotFoundException;
import org.izouir.inventory_service.mapper.StoredProductMapper;
import org.izouir.inventory_service.repository.StoredProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoredProductService {
    private final StoredProductRepository storedProductRepository;

    private static final String STORED_PRODUCT_NOT_FOUND_MESSAGE = "Stored product with id %s not found";
    private static final String EXCEEDING_CHANGE_AMOUNT_MESSAGE = "Change amount exceeds the stored one";

    @Transactional
    public StoredProductDto addAmount(final ChangeAmountRequestDto request) {
        final var storedProduct = storedProductRepository.findById(request.getStoredProductId())
                .orElseThrow(() -> new StoredProductNotFoundException(
                        String.format(STORED_PRODUCT_NOT_FOUND_MESSAGE, request.getStoredProductId())));
        storedProduct.setQuantity(storedProduct.getQuantity() + request.getAmount());
        return StoredProductMapper.toDto(storedProductRepository.save(storedProduct));
    }

    @Transactional
    public StoredProductDto subtractAmount(final ChangeAmountRequestDto request) {
        final var storedProduct = storedProductRepository.findById(request.getStoredProductId())
                .orElseThrow(() -> new StoredProductNotFoundException(
                        String.format(STORED_PRODUCT_NOT_FOUND_MESSAGE, request.getStoredProductId())));
        if (storedProduct.getQuantity() - request.getAmount() < 0) {
            throw new IllegalArgumentException(EXCEEDING_CHANGE_AMOUNT_MESSAGE);
        }
        storedProduct.setQuantity(storedProduct.getQuantity() - request.getAmount());
        return StoredProductMapper.toDto(storedProductRepository.save(storedProduct));
    }
}
