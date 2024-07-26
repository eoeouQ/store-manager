package org.izouir.inventory_service.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.dto.StoredProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/stored-products")
@Tag(name = "Stored product management")
public interface StoredProductAPI {
    @Operation(summary = "Add quantity of stored product to the store")
    @PostMapping("/add")
    ResponseEntity<StoredProductDto> addAmount(@RequestBody final ChangeAmountRequestDto request);

    @Operation(summary = "Subtract quantity of stored product from the store")
    @PostMapping("/subtract")
    ResponseEntity<StoredProductDto> subtractAmount(@RequestBody final ChangeAmountRequestDto request);
}
