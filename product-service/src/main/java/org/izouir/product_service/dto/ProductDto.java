package org.izouir.product_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Product")
public class ProductDto {
    @Schema(description = "Product ID", example = "1")
    private Long id;

    @Schema(description = "Product label", example = "Mobile phone")
    private String label;

    @Schema(description = "Product price", example = "10")
    private Integer price;
}
