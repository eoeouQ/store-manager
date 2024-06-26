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
    private final StoredProductRepository repository;

    @Transactional
    public StoredProductDto addAmount(final ChangeAmountRequestDto request) {
        final var storedProduct = repository.findById(request.getStoredProductId()).orElseThrow(
                () -> new StoredProductNotFoundException(
                        "Stored product with id " + request.getStoredProductId() + " not found"));
        storedProduct.setStored(storedProduct.getStored() + request.getAmount());
        return StoredProductMapper.toDto(repository.save(storedProduct));
    }

    @Transactional
    public StoredProductDto subtractAmount(final ChangeAmountRequestDto request) {
        final var storedProduct = repository.findById(request.getStoredProductId()).orElseThrow(
                () -> new StoredProductNotFoundException(
                        "Stored product with id " + request.getStoredProductId() + " not found"));
        if (storedProduct.getStored() - request.getAmount() < 0) {
            throw new IllegalArgumentException("Change amount is greater than stored one");
        }
        storedProduct.setStored(storedProduct.getStored() - request.getAmount());
        return StoredProductMapper.toDto(repository.save(storedProduct));
    }
}
