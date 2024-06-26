package org.izouir.product_service.mapper;

import org.izouir.product_service.dto.ProductDto;
import org.izouir.product_service.entity.Product;

public class ProductMapper {
    public static Product toEntity(final ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .label(productDto.getLabel())
                .price(productDto.getPrice())
                .build();
    }

    public static ProductDto toDto(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .label(product.getLabel())
                .price(product.getPrice())
                .build();
    }
}
