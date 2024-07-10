package org.izouir.product_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.exception.ProductNotFoundException;
import org.izouir.product_service.mapper.ProductMapper;
import org.izouir.product_service.repository.ProductRepository;
import org.izouir.product_service.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id %s not found";

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    @Override
    public ProductDto find(final Long id) {
        final var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id)));
        return ProductMapper.toDto(product);
    }

    @Override
    public ProductDto save(final ProductDto productDto) {
        var product = ProductMapper.toEntity(productDto);
        product = productRepository.save(product);
        return ProductMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto update(final ProductDto productDto) {
        final var product = productRepository.findById(productDto.getId()).orElseThrow(
                () -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productDto.getId())));
        if (productDto.getLabel() != null) {
            product.setLabel(productDto.getLabel());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        return ProductMapper.toDto(productRepository.save(product));
    }

    @Override
    public void delete(final Long id) {
        productRepository.deleteById(id);
    }
}
