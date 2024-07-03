package org.izouir.product_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product management")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Find all products")
    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Find product by ID")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> find(@PathVariable("productId") final Long productId) {
        return new ResponseEntity<>(productService.find(productId), HttpStatus.OK);
    }

    @Operation(summary = "Product creation")
    @PostMapping
    public ResponseEntity<ProductDto> save(@RequestBody final ProductDto product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Operation(summary = "Product update")
    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody final ProductDto product) {
        return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
    }

    @Operation(summary = "Product deletion by id")
    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable("productId") final Long productId) {
        productService.delete(productId);
    }
}
