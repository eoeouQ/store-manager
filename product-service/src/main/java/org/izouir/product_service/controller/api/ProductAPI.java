package org.izouir.product_service.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.izouir.product_service.dto.FiltersRequestDto;
import org.izouir.product_service.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/product")
@Tag(name = "Product management")
public interface ProductAPI {
    @Operation(summary = "Find all products")
    @GetMapping
    ResponseEntity<List<ProductDto>> findAll();

    @Operation(summary = "Find product by ID")
    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> find(@PathVariable("productId") final Long productId);

    @Operation(summary = "Product creation")
    @PostMapping
    ResponseEntity<ProductDto> save(@RequestBody @Valid final ProductDto product);

    @Operation(summary = "Product update")
    @PutMapping
    ResponseEntity<ProductDto> update(@RequestBody @Valid final ProductDto product);

    @Operation(summary = "Product deletion by id")
    @DeleteMapping("/{productId}")
    ResponseEntity<Void> delete(@PathVariable("productId") final Long productId);

    @Operation(summary = "Filtered products")
    @PostMapping("/search")
    ResponseEntity<List<ProductDto>> getProductsFiltered(@RequestBody final FiltersRequestDto request);
}
