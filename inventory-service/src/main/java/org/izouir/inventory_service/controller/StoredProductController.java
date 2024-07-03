package org.izouir.inventory_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.dto.StoredProductDto;
import org.izouir.inventory_service.service.StoredProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stored-products")
@RequiredArgsConstructor
@Tag(name = "Stored product management")
public class StoredProductController {
    private final StoredProductService storedProductService;

    @Operation(summary = "Add quantity of stored product to the store")
    @PostMapping("/add")
    public ResponseEntity<StoredProductDto> addAmount(@RequestBody final ChangeAmountRequestDto request) {
        return new ResponseEntity<>(storedProductService.addAmount(request), HttpStatus.OK);
    }

    @Operation(summary = "Subtract quantity of stored product from the store")
    @PostMapping("/subtract")
    public ResponseEntity<StoredProductDto> subtractAmount(@RequestBody final ChangeAmountRequestDto request) {
        return new ResponseEntity<>(storedProductService.subtractAmount(request), HttpStatus.OK);
    }
}
