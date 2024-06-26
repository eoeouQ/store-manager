package org.izouir.product_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Менеджмент продуктов")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Найти все продукты")
    @GetMapping
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @Operation(summary = "Найти продукт по id")
    @GetMapping("/{productId}")
    public ProductDto find(@PathVariable("productId") final Long productId) {
        return productService.find(productId);
    }

    @Operation(summary = "Создание продукта")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductDto save(@RequestBody final ProductDto product) {
        return productService.save(product);
    }

    @Operation(summary = "Обновление продукта")
    @PutMapping
    public ProductDto update(@RequestBody final ProductDto product) {
        return productService.update(product);
    }

    @Operation(summary = "Удаление продукта по id")
    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("productId") final Long productId) {
        productService.delete(productId);
    }
}
