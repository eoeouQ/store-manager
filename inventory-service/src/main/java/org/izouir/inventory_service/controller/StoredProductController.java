package org.izouir.inventory_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.dto.StoredProductDto;
import org.izouir.inventory_service.service.StoredProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stored-products")
@RequiredArgsConstructor
@Tag(name = "Менеджмент хранимых продуктов")
public class StoredProductController {
    private final StoredProductService storedProductService;

    @Operation(summary = "Добавить партию продукта на склад")
    @PostMapping("/add")
    public StoredProductDto addAmount(@RequestBody final ChangeAmountRequestDto request) {
        return storedProductService.addAmount(request);
    }

    @Operation(summary = "Убрать партию продукта со склада")
    @PostMapping("/subtract")
    public StoredProductDto subtractAmount(@RequestBody final ChangeAmountRequestDto request) {
        return storedProductService.subtractAmount(request);
    }
}
