package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Order position")
public class OrderPositionDto {
    @Schema(description = "Ordered product")
    private StoredProductDto storedProduct;

    @Schema(description = "Ordered product quantity", example = "10")
    private Integer quantity;
}
