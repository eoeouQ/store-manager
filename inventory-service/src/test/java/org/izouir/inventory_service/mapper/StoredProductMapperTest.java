package org.izouir.inventory_service.mapper;

import org.izouir.shared_lib.entity.Product;
import org.izouir.shared_lib.entity.Store;
import org.izouir.shared_lib.entity.StoreLocation;
import org.izouir.shared_lib.entity.StoredProduct;
import org.izouir.shared_lib.entity.StoredProductKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoredProductMapperTest {
    @Test
    public void toDto_ShouldPass() {
        final var product = Product.builder()
                .id(1L)
                .label("TEST_PRODUCT")
                .price(1)
                .build();
        final var store = Store.builder()
                .id(1L)
                .name("TEST_STORE")
                .location(StoreLocation.LOCATION_BELARUS)
                .build();
        final var storedProductKey = StoredProductKey.builder()
                .product(product)
                .store(store)
                .build();
        final var storedProduct = StoredProduct.builder()
                .id(storedProductKey)
                .quantity(10)
                .build();
        final var storedProductDto = StoredProductMapper.toDto(storedProduct);
        final var productDto = storedProductDto.getProduct();

        assertEquals(productDto.getId(), product.getId());
        assertEquals(productDto.getLabel(), product.getLabel());
        assertEquals(productDto.getPrice(), product.getPrice());

        assertEquals(storedProductDto.getStoreId(), store.getId());
        assertEquals(storedProductDto.getQuantity(), storedProduct.getQuantity());
    }
}
