package org.izouir.product_service.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.exception.ProductNotFoundException;
import org.izouir.product_service.mapper.ProductMapper;
import org.izouir.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    @Transactional
    public List<ProductDto> findAll() {
        return repository.findAll().stream().map(ProductMapper::toDto).toList();
    }

    @Transactional
    public ProductDto find(final Long id) {
        return ProductMapper.toDto(repository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + id + " not found")));
    }

    @Transactional
    public ProductDto save(final ProductDto productDto) {
        return ProductMapper.toDto(repository.save(ProductMapper.toEntity(productDto)));
    }

    @Transactional
    public ProductDto update(final ProductDto productDto) {
        final var product = repository.findById(productDto.getId()).orElseThrow(
                () -> new ProductNotFoundException("Product with id " + productDto.getId() + " not found"));
        product.setLabel(productDto.getLabel());
        product.setPrice(productDto.getPrice());
        return ProductMapper.toDto(repository.save(product));
    }

    @Transactional
    public void delete(final Long id) {
        repository.deleteById(id);
    }
}
