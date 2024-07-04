package org.izouir.product_service.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.izouir.product_service.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<ProductDto> save(@RequestBody final ProductDto product);

    @Operation(summary = "Product update")
    @PutMapping
    ResponseEntity<ProductDto> update(@RequestBody final ProductDto product);

    @Operation(summary = "Product deletion by id")
    @DeleteMapping("/{productId}")
    ResponseEntity<Void> delete(@PathVariable("productId") final Long productId);
}
