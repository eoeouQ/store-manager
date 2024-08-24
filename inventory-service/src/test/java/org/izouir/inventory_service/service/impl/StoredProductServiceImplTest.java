package org.izouir.inventory_service.service.impl;

import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.inventory_service.entity.InventoryOperation;
import org.izouir.inventory_service.entity.Product;
import org.izouir.inventory_service.entity.Store;
import org.izouir.inventory_service.entity.StoreLocation;
import org.izouir.inventory_service.entity.StoredProduct;
import org.izouir.inventory_service.entity.StoredProductKey;
import org.izouir.inventory_service.exception.StoredProductNotFoundException;
import org.izouir.inventory_service.repository.ProductRepository;
import org.izouir.inventory_service.repository.StoreRepository;
import org.izouir.inventory_service.repository.StoredProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

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
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StoreRepository storeRepository;

    @BeforeEach
    public void init() {
        when(productRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(Product.builder()
                        .id(1L)
                        .label("TEST")
                        .price(1)
                        .build()));
        when(storeRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(Store.builder()
                        .id(1L)
                        .name("TEST")
                        .location(StoreLocation.LOCATION_BELARUS)
                        .build()));
    }

    @Test
    public void addAmount_ShouldAdd() {
        when(storedProductRepository.findById(Mockito.any(StoredProductKey.class)))
                .thenReturn(Optional.of(StoredProduct.builder()
                        .id(StoredProductKey.builder()
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
                                .build())
                        .quantity(10)
                        .build()));
        when(storedProductRepository.save(Mockito.any(StoredProduct.class)))
                .thenReturn(StoredProduct.builder()
                        .id(StoredProductKey.builder()
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
                                .build())
                        .quantity(20)
                        .build());

        storedProductService.changeAmount(ChangeAmountRequestDto.builder()
                .productId(1L)
                .storeId(1L)
                .amount(10)
                .build(), InventoryOperation.OPERATION_ADD);

        verify(storedProductRepository, times(1)).findById(Mockito.any(StoredProductKey.class));
        verify(storedProductRepository, times(1)).save(Mockito.any(StoredProduct.class));
    }

    @Test
    public void addAmount_ShouldNotAdd() {
        when(storedProductRepository.findById(Mockito.any(StoredProductKey.class)))
                .thenThrow(StoredProductNotFoundException.class);

        assertThrows(StoredProductNotFoundException.class, () -> storedProductService.changeAmount(ChangeAmountRequestDto.builder()
                .productId(-1L)
                .storeId(-1L)
                .amount(10)
                .build(), InventoryOperation.OPERATION_ADD));
        verify(storedProductRepository, times(1)).findById(Mockito.any(StoredProductKey.class));
        verify(storedProductRepository, times(0)).save(Mockito.any(StoredProduct.class));
    }

    @Test
    public void subtractAmount_ShouldSubtract() {
        when(storedProductRepository.findById(Mockito.any(StoredProductKey.class)))
                .thenReturn(Optional.of(StoredProduct.builder()
                        .id(StoredProductKey.builder()
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
                                .build())
                        .quantity(20)
                        .build()));
        when(storedProductRepository.save(Mockito.any(StoredProduct.class)))
                .thenReturn(StoredProduct.builder()
                        .id(StoredProductKey.builder()
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
                                .build())
                        .quantity(10)
                        .build());

        storedProductService.changeAmount(ChangeAmountRequestDto.builder()
                .productId(1L)
                .storeId(1L)
                .amount(10)
                .build(), InventoryOperation.OPERATION_SUBTRACT);

        verify(storedProductRepository, times(1)).findById(Mockito.any(StoredProductKey.class));
        verify(storedProductRepository, times(1)).save(Mockito.any(StoredProduct.class));
    }

    @Test
    public void subtractAmount_ShouldNotSubtract() {
        when(storedProductRepository.findById(Mockito.any(StoredProductKey.class)))
                .thenReturn(Optional.of(StoredProduct.builder()
                        .id(StoredProductKey.builder()
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
                                .build())
                        .quantity(0)
                        .build()));
        when(storedProductRepository.save(Mockito.any(StoredProduct.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> storedProductService.changeAmount(ChangeAmountRequestDto.builder()
                .productId(1L)
                .storeId(1L)
                .amount(10)
                .build(), InventoryOperation.OPERATION_SUBTRACT));
        verify(storedProductRepository, times(1)).findById(Mockito.any(StoredProductKey.class));
        verify(storedProductRepository, times(0)).save(Mockito.any(StoredProduct.class));

        when(storedProductRepository.findById(Mockito.any(StoredProductKey.class)))
                .thenThrow(StoredProductNotFoundException.class);

        assertThrows(StoredProductNotFoundException.class, () -> storedProductService.changeAmount(ChangeAmountRequestDto.builder()
                .productId(-1L)
                .storeId(-1L)
                .amount(10)
                .build(), InventoryOperation.OPERATION_SUBTRACT));
        verify(storedProductRepository, times(2)).findById(Mockito.any(StoredProductKey.class));
        verify(storedProductRepository, times(0)).save(Mockito.any(StoredProduct.class));
    }
}
