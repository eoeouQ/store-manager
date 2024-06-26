package org.izouir.order_service.mapper;

import org.izouir.order_service.dto.ProductDto;
import org.izouir.order_service.entity.Product;

public class ProductMapper {
    public static ProductDto toDto(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .label(product.getLabel())
                .price(product.getPrice())
                .build();
    }
}
