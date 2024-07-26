package org.izouir.inventory_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.izouir.inventory_service.controller.api.StoredProductAPI;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.dto.StoredProductDto;
import org.izouir.inventory_service.service.StoredProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoredProductController implements StoredProductAPI {
    private final StoredProductService storedProductService;

    @Override
    public ResponseEntity<StoredProductDto> addAmount(@RequestBody @Valid final ChangeAmountRequestDto request) {
        return new ResponseEntity<>(storedProductService.addAmount(request), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StoredProductDto> subtractAmount(@RequestBody @Valid final ChangeAmountRequestDto request) {
        return new ResponseEntity<>(storedProductService.subtractAmount(request), HttpStatus.OK);
    }
}
