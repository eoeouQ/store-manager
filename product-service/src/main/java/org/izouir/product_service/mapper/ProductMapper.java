package org.izouir.product_service.mapper;

import lombok.experimental.UtilityClass;
import org.izouir.product_service.dto.ProductDto;
import org.izouir.store_manager_entities.entity.Product;

import java.util.List;

@UtilityClass
public class ProductMapper {
    public Product toEntity(final ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .label(productDto.getLabel())
                .price(productDto.getPrice())
                .build();
    }

    public ProductDto toDto(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .label(product.getLabel())
                .price(product.getPrice())
                .build();
    }

    public List<ProductDto> toDtoList(final List<Product> orderList) {
        return orderList.stream()
                .map(ProductMapper::toDto)
                .toList();
    }
}
