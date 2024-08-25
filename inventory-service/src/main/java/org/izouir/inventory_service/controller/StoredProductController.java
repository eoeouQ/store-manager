package org.izouir.inventory_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.izouir.inventory_service.controller.api.StoredProductAPI;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.service.StoredProductService;
import org.izouir.shared_lib.entity.InventoryOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoredProductController implements StoredProductAPI {
    private final StoredProductService storedProductService;

    @Override
    public ResponseEntity<Void> changeAmount(@RequestBody @Valid final ChangeAmountRequestDto request,
                                             @PathVariable("operation") final InventoryOperation operation) {
        storedProductService.changeAmount(request, operation);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
