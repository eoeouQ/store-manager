package org.izouir.inventory_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.entity.InventoryOperation;
import org.izouir.inventory_service.service.ChangeAmountListenService;
import org.izouir.inventory_service.service.StoredProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeAmountListenServiceImpl implements ChangeAmountListenService {
    private final StoredProductService storedProductService;

    @Override
    @KafkaListener(topics = "${spring.kafka.consumer.properties.topic-add}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "requestListener")
    public void listenToAdd(final ChangeAmountRequestDto request) {
        storedProductService.changeAmount(request, InventoryOperation.OPERATION_ADD);
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.consumer.properties.topic-subtract}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "requestListener")
    public void listenToSubtract(final ChangeAmountRequestDto request) {
        storedProductService.changeAmount(request, InventoryOperation.OPERATION_SUBTRACT);
    }
}
