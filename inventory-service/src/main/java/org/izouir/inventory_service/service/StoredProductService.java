package org.izouir.inventory_service.service;

import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.entity.InventoryOperation;


public interface StoredProductService {
    void changeAmount(final ChangeAmountRequestDto request, final InventoryOperation operation);
}
