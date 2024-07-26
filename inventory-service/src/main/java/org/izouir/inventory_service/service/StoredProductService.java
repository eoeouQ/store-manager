package org.izouir.inventory_service.service;

import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.dto.StoredProductDto;


public interface StoredProductService {
    StoredProductDto addAmount(final ChangeAmountRequestDto request);

    StoredProductDto subtractAmount(final ChangeAmountRequestDto request);
}
