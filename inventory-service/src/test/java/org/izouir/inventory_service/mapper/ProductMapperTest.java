package org.izouir.inventory_service.mapper;

import org.izouir.store_manager_entities.entity.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {
    @Test
    public void toDto_ShouldPass() {
        final var product = Product.builder()
                .id(1L)
                .label("TEST")
                .price(1)
                .build();
        final var productDto = ProductMapper.toDto(product);

        assertEquals(productDto.getId(), product.getId());
        assertEquals(productDto.getLabel(), product.getLabel());
        assertEquals(productDto.getPrice(), product.getPrice());
    }
}
