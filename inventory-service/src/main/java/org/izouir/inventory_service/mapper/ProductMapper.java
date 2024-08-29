package org.izouir.inventory_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.inventory_service.dto.ProductDto;
import org.izouir.store_manager_entities.entity.Product;

@UtilityClass
public class ProductMapper {
    public ProductDto toDto(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .label(product.getLabel())
                .price(product.getPrice())
                .build();
    }
}
