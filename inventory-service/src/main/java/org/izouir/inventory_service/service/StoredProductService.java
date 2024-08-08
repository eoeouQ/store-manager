package org.izouir.inventory_service.service;

import org.izouir.inventory_service.dto.ChangeAmountRequestDto;


public interface StoredProductService {
    void addAmount(final ChangeAmountRequestDto request);

    void subtractAmount(final ChangeAmountRequestDto request);
}
