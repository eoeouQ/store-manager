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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
    public void findAll_ShouldFind() {
        when(productRepository.findAll())
                .thenReturn(products);

        final var returnedProductDtos = productService.findAll();

        assertNotNull(returnedProductDtos);
        assertEquals(3, returnedProductDtos.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void findAll_ShouldNotFound() {
        when(productRepository.findAll())
                .thenReturn(new ArrayList<>());

        final var returnedProductDtos = productService.findAll();

        assertNotNull(returnedProductDtos);
        assertEquals(0, returnedProductDtos.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void findById_ShouldFind() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenAnswer(invocation -> {
                    final var inputId = (Long) invocation.getArgument(0);
                    return products.stream()
                            .filter(product -> product.getId().equals(inputId))
                            .findFirst();
                });

        final var returnedProductDto = productService.find(1L);

        assertNotNull(returnedProductDto);
        assertEquals(1L, returnedProductDto.getId());
        verify(productRepository, times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    public void findById_ShouldNotFound() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenAnswer(invocation -> {
                    final var inputId = (Long) invocation.getArgument(0);
                    return products.stream()
                            .filter(product -> product.getId().equals(inputId))
                            .findFirst();
                });

        assertThrows(ProductNotFoundException.class, () -> productService.find(-1L));
        assertThrows(ProductNotFoundException.class, () -> productService.find(10L));
        verify(productRepository, times(2)).findById(Mockito.any(Long.class));
    }

    @Test
    public void save_ShouldSave() {
        when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> {
                    final var inputProduct = (Product) invocation.getArgument(0);
                    if (products.stream().noneMatch(
                            product -> product.getId().equals(inputProduct.getId()))
                            && (inputProduct.getId() == null
                            || inputProduct.getLabel() == null
                            || inputProduct.getPrice() == null)) {
                        throw new IllegalArgumentException();
                    }
                    products = new ArrayList<>(products.stream()
                            .filter(product -> !product.getId().equals(inputProduct.getId()))
                            .toList());
                    products.add(inputProduct);
                    return inputProduct;
                });

        final var productDto = ProductDto.builder()
                .id(4L)
                .label("TEST4")
                .price(4)
                .build();
        final var savedProduct = productService.save(productDto);

        assertNotNull(savedProduct);
        assertEquals(4, products.size());
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void save_ShouldNotSave() {
        when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> {
                    final var inputProduct = (Product) invocation.getArgument(0);
                    if (products.stream().noneMatch(
                            product -> product.getId().equals(inputProduct.getId()))
                            && (inputProduct.getId() == null
                            || inputProduct.getLabel() == null
                            || inputProduct.getPrice() == null)) {
                        throw new IllegalArgumentException();
                    }
                    products = new ArrayList<>(products.stream()
                            .filter(product -> !product.getId().equals(inputProduct.getId()))
                            .toList());
                    products.add(inputProduct);
                    return inputProduct;
                });

        final var productDto = ProductDto.builder()
                .id(null)
                .label(null)
                .price(null)
                .build();

        assertThrows(IllegalArgumentException.class, () -> productService.save(productDto));
        assertThrows(NullPointerException.class, () -> productService.save(null));
        assertEquals(3, products.size());
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void updateFull_ShouldUpdate() {
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
                    if (products.stream().noneMatch(
                            product -> product.getId().equals(inputProduct.getId()))
                            && (inputProduct.getId() == null
                            || inputProduct.getLabel() == null
                            || inputProduct.getPrice() == null)) {
                        throw new IllegalArgumentException();
                    }
                    products = new ArrayList<>(products.stream()
                            .filter(product -> !product.getId().equals(inputProduct.getId()))
                            .toList());
                    products.add(inputProduct);
                    return inputProduct;
                });

        final var productDto = ProductDto.builder()
                .id(2L)
                .label("TEST_UPDATE")
                .price(4)
                .build();
        final var updatedProduct = productService.update(productDto);

        assertNotNull(updatedProduct);
        assertEquals(3, products.size());
        assertEquals("TEST_UPDATE", updatedProduct.getLabel());
        assertEquals(4, updatedProduct.getPrice());
        verify(productRepository, times(1)).findById(Mockito.any(Long.class));
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void updatePartial_ShouldUpdate() {
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
                    if (products.stream().noneMatch(
                            product -> product.getId().equals(inputProduct.getId()))
                            && (inputProduct.getId() == null
                            || inputProduct.getLabel() == null
                            || inputProduct.getPrice() == null)) {
                        throw new IllegalArgumentException();
                    }
                    products = new ArrayList<>(products.stream()
                            .filter(product -> !product.getId().equals(inputProduct.getId()))
                            .toList());
                    products.add(inputProduct);
                    return inputProduct;
                });

        var productDto = ProductDto.builder()
                .id(2L)
                .label("TEST_UPDATE")
                .build();
        var updatedProduct = productService.update(productDto);

        assertNotNull(updatedProduct);
        assertEquals(3, products.size());
        assertEquals("TEST_UPDATE", updatedProduct.getLabel());
        assertEquals(2, updatedProduct.getPrice());

        productDto = ProductDto.builder()
                .id(2L)
                .price(4)
                .build();
        updatedProduct = productService.update(productDto);

        assertNotNull(updatedProduct);
        assertEquals(3, products.size());
        assertEquals("TEST_UPDATE", updatedProduct.getLabel());
        assertEquals(4, updatedProduct.getPrice());
        verify(productRepository, times(2)).findById(Mockito.any(Long.class));
        verify(productRepository, times(2)).save(Mockito.any(Product.class));
    }

    @Test
    public void updateNonexistent_ShouldNotUpdate() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenAnswer(invocation -> {
                    final var inputId = (Long) invocation.getArgument(0);
                    return products.stream()
                            .filter(product -> product.getId().equals(inputId))
                            .findFirst();
                });
        when(productRepository.save(Mockito.any(Product.class)))
                .thenThrow(IllegalArgumentException.class);

        final var productDto = ProductDto.builder()
                .id(-1L)
                .build();

        assertThrows(ProductNotFoundException.class, () -> productService.update(productDto));
        verify(productRepository, times(1)).findById(Mockito.any(Long.class));
        verify(productRepository, times(0)).save(Mockito.any(Product.class));
    }

    @Test
    public void delete_ShouldPass() {
        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(Mockito.any(Long.class));
    }
}
