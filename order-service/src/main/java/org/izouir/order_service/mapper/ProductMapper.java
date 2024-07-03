package org.izouir.order_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.order_service.dto.ProductDto;
import org.izouir.order_service.entity.Product;

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
