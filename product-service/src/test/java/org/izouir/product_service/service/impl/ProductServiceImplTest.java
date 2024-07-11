package org.izouir.product_service.service.impl;

import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.entity.Product;
import org.izouir.product_service.exception.ProductNotFoundException;
import org.izouir.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    private List<Product> products;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        products.add(Product.builder()
                .id(1L)
                .label("TEST1")
                .price(1)
                .build());
        products.add(Product.builder()
                .id(2L)
                .label("TEST2")
                .price(2)
                .build());
        products.add(Product.builder()
                .id(3L)
                .label("TEST3")
                .price(3)
                .build());
    }

    @Test
    public void findAll_ShouldPass() {
        when(productRepository.findAll())
                .thenReturn(products);

        final var returnedProductDtos = productService.findAll();

        assertEquals(3, returnedProductDtos.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void findById_ShouldPass() {
        final var searchId = 1L;
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenAnswer(invocation -> {
                    final var inputId = (Long) invocation.getArgument(0);
                    return products.stream()
                            .filter(product -> Objects.equals(product.getId(), inputId))
                            .findFirst();
                });

        final var returnedProductDto = productService.find(searchId);

        assertEquals(searchId, returnedProductDto.getId());
        assertThrows(ProductNotFoundException.class, () -> productService.find(10L));
        verify(productRepository, times(2)).findById(Mockito.any(Long.class));
    }

    @Test
    public void save_ShouldPass() {
        final var productDto = ProductDto.builder()
                .id(4L)
                .label("TEST4")
                .price(4)
                .build();
        when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> {
                    final var inputProduct = (Product) invocation.getArgument(0);
                    products = new ArrayList<>(products.stream()
                            .filter(product -> !product.getId().equals(inputProduct.getId()))
                            .toList());
                    products.add(inputProduct);
                    return inputProduct;
                });

        final var savedProduct = productService.save(productDto);

        assertNotNull(savedProduct);
        assertEquals(4, products.size());
        assertThrows(NullPointerException.class, () -> productService.save(null));
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void update_ShouldPass() {
        final var productDto = ProductDto.builder()
                .id(2L)
                .label("TEST_UPDATE")
                .build();
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenAnswer(invocation -> {
                    final var inputId = (Long) invocation.getArgument(0);
                    return products.stream()
                            .filter(product -> product.getId().equals(inputId))
                            .findFirst();
                });
        when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> {
                    final var inputProduct = (Product) invocation.getArgument(0);
                    products = new ArrayList<>(products.stream()
                            .filter(product -> !product.getId().equals(inputProduct.getId()))
                            .toList());
                    products.add(inputProduct);
                    return inputProduct;
                });

        final var updatedProduct = productService.update(productDto);

        assertNotNull(updatedProduct);
        assertEquals(3, products.size());
        assertEquals("TEST_UPDATE", updatedProduct.getLabel());
        assertEquals(2, updatedProduct.getPrice());
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void delete_ShouldPass() {
        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(Mockito.any(Long.class));
    }
}
