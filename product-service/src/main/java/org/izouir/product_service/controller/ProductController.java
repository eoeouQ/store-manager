package org.izouir.product_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.izouir.product_service.controller.api.ProductAPI;
import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductAPI {
    private final ProductService productService;

    @Override
    public ResponseEntity<List<ProductDto>> findAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductDto> find(@PathVariable("productId") final Long productId) {
        return new ResponseEntity<>(productService.find(productId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductDto> save(@RequestBody @Valid final ProductDto product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProductDto> update(@RequestBody @Valid final ProductDto product) {
        return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable("productId") final Long productId) {
        productService.delete(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
