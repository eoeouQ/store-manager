package org.izouir.inventory_service.mapper;

import org.izouir.inventory_service.dto.StoredProductDto;
import org.izouir.inventory_service.entity.StoredProduct;

public class StoredProductMapper {
    public static StoredProductDto toDto(final StoredProduct storedProduct) {
        return StoredProductDto.builder()
                .id(storedProduct.getId())
                .product(ProductMapper.toDto(storedProduct.getProduct()))
                .storeId(storedProduct.getStore().getId())
                .stored(storedProduct.getStored())
                .build();
    }
}
