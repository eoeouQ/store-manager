package org.izouir.inventory_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Продукт")
public class ProductDto {
    @Schema(description = "Идентификатор продукта", example = "1")
    private Long id;

    @Schema(description = "Название продукта", example = "Телефон")
    private String label;

    @Schema(description = "Цена продукта", example = "10.50")
    private BigDecimal price;
}
