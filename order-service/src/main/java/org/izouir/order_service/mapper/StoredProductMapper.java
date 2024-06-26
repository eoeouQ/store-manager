package org.izouir.order_service.mapper;

import org.izouir.order_service.dto.StoredProductDto;
import org.izouir.order_service.entity.StoredProduct;

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
