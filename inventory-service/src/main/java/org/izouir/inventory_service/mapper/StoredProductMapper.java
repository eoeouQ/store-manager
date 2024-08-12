package org.izouir.inventory_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.inventory_service.dto.StoredProductDto;
import org.izouir.inventory_service.entity.StoredProduct;

@UtilityClass
public class StoredProductMapper {
    public StoredProductDto toDto(final StoredProduct storedProduct) {
        return StoredProductDto.builder()
                .product(ProductMapper.toDto(storedProduct.getId().getProduct()))
                .storeId(storedProduct.getId().getStore().getId())
                .quantity(storedProduct.getQuantity())
                .build();
    }
}
