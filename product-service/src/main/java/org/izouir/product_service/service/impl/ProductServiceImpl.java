package org.izouir.product_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.izouir.product_service.dto.FiltersRequestDto;
import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.exception.ProductNotFoundException;
import org.izouir.product_service.mapper.ProductMapper;
import org.izouir.product_service.repository.ProductRepository;
import org.izouir.product_service.service.ProductService;
import org.izouir.store_manager_entities.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SpecificationServiceImpl<Product> orderSpecificationService;

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
        productRepository.findById(productDto.getId()).orElseThrow(
                () -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productDto.getId())));
        final var product = productRepository.save(ProductMapper.toEntity(productDto));
        return ProductMapper.toDto(product);
    }

    @Override
    public void delete(final Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> getProductsFiltered(final FiltersRequestDto request) {
        final var filterSpecification = orderSpecificationService
                .getSearchSpecification(request.getFilters());
        final var orders = productRepository.findAll(filterSpecification);
        return ProductMapper.toDtoList(orders);
    }
}
