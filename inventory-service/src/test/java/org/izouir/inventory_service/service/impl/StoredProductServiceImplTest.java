package org.izouir.inventory_service.service.impl;

import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.entity.Product;
import org.izouir.inventory_service.entity.Store;
import org.izouir.inventory_service.entity.StoreLocation;
import org.izouir.inventory_service.entity.StoredProduct;
import org.izouir.inventory_service.repository.StoredProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StoredProductServiceImplTest {
    @InjectMocks
    private StoredProductServiceImpl storedProductService;

    @Mock
    private StoredProductRepository storedProductRepository;

    @Test
    public void addAmount_ShouldAdd() {
        when(storedProductRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(StoredProduct.builder()
                        .product(Product.builder()
                                .id(1L)
                                .label("TEST")
                                .price(1)
                                .build())
                        .store(Store.builder()
                                .id(1L)
                                .name("TEST")
                                .location(StoreLocation.LOCATION_BELARUS)
                                .build())
                        .quantity(10)
                        .build()));
        when(storedProductRepository.save(Mockito.any(StoredProduct.class)))
                .thenReturn(StoredProduct.builder()
                        .product(Product.builder()
                                .id(1L)
                                .label("TEST")
                                .price(1)
                                .build())
                        .store(Store.builder()
                                .id(1L)
                                .name("TEST")
                                .location(StoreLocation.LOCATION_BELARUS)
                                .build())
                        .quantity(20)
                        .build());

        final var updatedStoredProduct = storedProductService.addAmount(ChangeAmountRequestDto.builder()
                .storedProductId(1L)
                .amount(10)
                .build());

        assertNotNull(updatedStoredProduct);
        assertEquals(20, updatedStoredProduct.getQuantity());
        verify(storedProductRepository, times(1)).findById(Mockito.any(Long.class));
        verify(storedProductRepository, times(1)).save(Mockito.any(StoredProduct.class));
    }

    @Test
    public void subtractAmount_ShouldSubtract() {
        when(storedProductRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(StoredProduct.builder()
                        .product(Product.builder()
                                .id(1L)
                                .label("TEST")
                                .price(1)
                                .build())
                        .store(Store.builder()
                                .id(1L)
                                .name("TEST")
                                .location(StoreLocation.LOCATION_BELARUS)
                                .build())
                        .quantity(20)
                        .build()));
        when(storedProductRepository.save(Mockito.any(StoredProduct.class)))
                .thenReturn(StoredProduct.builder()
                        .product(Product.builder()
                                .id(1L)
                                .label("TEST")
                                .price(1)
                                .build())
                        .store(Store.builder()
                                .id(1L)
                                .name("TEST")
                                .location(StoreLocation.LOCATION_BELARUS)
                                .build())
                        .quantity(10)
                        .build());

        final var updatedStoredProduct = storedProductService.subtractAmount(ChangeAmountRequestDto.builder()
                .storedProductId(1L)
                .amount(10)
                .build());

        assertNotNull(updatedStoredProduct);
        assertEquals(10, updatedStoredProduct.getQuantity());
        verify(storedProductRepository, times(1)).findById(Mockito.any(Long.class));
        verify(storedProductRepository, times(1)).save(Mockito.any(StoredProduct.class));
    }

    @Test
    public void subtractAmount_ShouldNotSubtract() {
        when(storedProductRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(StoredProduct.builder()
                        .product(Product.builder()
                                .id(1L)
                                .label("TEST")
                                .price(1)
                                .build())
                        .store(Store.builder()
                                .id(1L)
                                .name("TEST")
                                .location(StoreLocation.LOCATION_BELARUS)
                                .build())
                        .quantity(0)
                        .build()));
        when(storedProductRepository.save(Mockito.any(StoredProduct.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> storedProductService.subtractAmount(ChangeAmountRequestDto.builder()
                .storedProductId(1L)
                .amount(10)
                .build()));
        verify(storedProductRepository, times(1)).findById(Mockito.any(Long.class));
        verify(storedProductRepository, times(0)).save(Mockito.any(StoredProduct.class));
    }
}
