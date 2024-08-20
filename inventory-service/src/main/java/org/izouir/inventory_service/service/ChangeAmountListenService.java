package org.izouir.inventory_service.service;

import org.izouir.inventory_service.dto.ChangeAmountRequestDto;

public interface ChangeAmountListenService {
    void listenToAdd(final ChangeAmountRequestDto request);

    void listenToSubtract(final ChangeAmountRequestDto request);
}
