package org.izouir.product_service.service;

import org.izouir.product_service.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto find(final Long id);

    ProductDto save(final ProductDto productDto);

    ProductDto update(final ProductDto productDto);

    void delete(final Long id);
}
