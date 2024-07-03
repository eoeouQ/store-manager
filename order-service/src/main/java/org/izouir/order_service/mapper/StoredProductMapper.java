package org.izouir.order_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.order_service.dto.StoredProductDto;
import org.izouir.order_service.entity.StoredProduct;

@UtilityClass
public class StoredProductMapper {
    public StoredProductDto toDto(final StoredProduct storedProduct) {
        return StoredProductDto.builder()
                .id(storedProduct.getId())
                .product(ProductMapper.toDto(storedProduct.getProduct()))
                .storeId(storedProduct.getStore().getId())
                .quantity(storedProduct.getQuantity())
                .build();
    }
}
