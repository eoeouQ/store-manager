package org.izouir.product_service.service.impl;

import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.entity.Product;
import org.izouir.product_service.exception.ProductNotFoundException;
import org.izouir.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void findAll_ShouldFind() {
        final var products = new ArrayList<Product>();
        products.add(Product.builder()
                .id(1L)
                .build());
        products.add(Product.builder()
                .id(2L)
                .build());
        products.add(Product.builder()
                .id(3L)
                .build());
        when(productRepository.findAll())
                .thenReturn(products);

        final var foundProductDtos = productService.findAll();

        assertNotNull(foundProductDtos);
        assertEquals(3, foundProductDtos.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void findAll_ShouldNotFound() {
        when(productRepository.findAll())
                .thenReturn(new ArrayList<>());

        final var foundProductDtos = productService.findAll();

        assertNotNull(foundProductDtos);
        assertEquals(0, foundProductDtos.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void findById_ShouldFind() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.ofNullable(Product.builder()
                        .id(1L)
                        .build()));

        final var foundProductDto = productService.find(1L);

        assertNotNull(foundProductDto);
        assertEquals(1L, foundProductDto.getId());
        verify(productRepository, times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    public void findById_ShouldNotFound() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.find(-1L));
        assertThrows(ProductNotFoundException.class, () -> productService.find(10L));
        verify(productRepository, times(2)).findById(Mockito.any(Long.class));
    }

    @Test
    public void save_ShouldSave() {
        final var product = Product.builder()
                .id(4L)
                .build();
        when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(product);

        final var savedProduct = productService.save(ProductDto.builder()
                .id(4L)
                .build());

        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void save_ShouldNotSave() {
        final var productDto = ProductDto.builder()
                .id(null)
                .label(null)
                .price(null)
                .build();
        when(productRepository.save(Mockito.any(Product.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> productService.save(productDto));
        assertThrows(NullPointerException.class, () -> productService.save(null));
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void updateFull_ShouldUpdate() {
        when(productRepository.findById(2L))
                .thenReturn(Optional.of(Product.builder()
                        .id(2L)
                        .label("TEST")
                        .price(2)
                        .build()));
        when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(Product.builder()
                        .id(2L)
                        .label("TEST_UPDATE")
                        .price(4)
                        .build());

        final var updatedProduct = productService.update(ProductDto.builder()
                .id(2L)
                .label("TEST_UPDATE")
                .price(4)
                .build());

        assertNotNull(updatedProduct);
        assertEquals("TEST_UPDATE", updatedProduct.getLabel());
        assertEquals(4, updatedProduct.getPrice());
        verify(productRepository, times(1)).findById(Mockito.any(Long.class));
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void updatePartial_ShouldUpdate() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.ofNullable(Product.builder()
                        .id(2L)
                        .label("TEST")
                        .price(2)
                        .build()));
        when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(Product.builder()
                        .id(2L)
                        .label("TEST_UPDATE")
                        .price(2)
                        .build());
        var updatedProduct = productService.update(ProductDto.builder()
                .id(2L)
                .label("TEST_UPDATE")
                .build());

        assertNotNull(updatedProduct);
        assertEquals("TEST_UPDATE", updatedProduct.getLabel());
        assertEquals(2, updatedProduct.getPrice());

        when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(Product.builder()
                        .id(2L)
                        .label("TEST")
                        .price(4)
                        .build());
        updatedProduct = productService.update(ProductDto.builder()
                .id(2L)
                .price(4)
                .build());

        assertNotNull(updatedProduct);
        assertEquals("TEST", updatedProduct.getLabel());
        assertEquals(4, updatedProduct.getPrice());
        verify(productRepository, times(2)).findById(Mockito.any(Long.class));
        verify(productRepository, times(2)).save(Mockito.any(Product.class));
    }

    @Test
    public void updateInvalid_ShouldNotUpdate() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.ofNullable(Product.builder()
                        .id(2L)
                        .build()));
        when(productRepository.save(Mockito.any(Product.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> productService.update(ProductDto.builder()
                .id(2L)
                .price(-1)
                .build()));
        verify(productRepository, times(1)).findById(Mockito.any(Long.class));
        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test
    public void updateNonexistent_ShouldNotUpdate() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.update(ProductDto.builder()
                .id(-1L)
                .price(-1)
                .build()));
        verify(productRepository, times(1)).findById(Mockito.any(Long.class));
        verify(productRepository, times(0)).save(Mockito.any(Product.class));
    }

    @Test
    public void delete_ShouldPass() {
        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(Mockito.any(Long.class));
    }
}
