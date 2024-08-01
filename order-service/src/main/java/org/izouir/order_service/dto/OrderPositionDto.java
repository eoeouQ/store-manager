package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Order position")
public class OrderPositionDto {
    @NotNull(message = "Product ID can't be null")
    @Positive(message = "Product ID must be positive number")
    @Schema(description = "Product ID", example = "1")
    private Long productId;

    @NotNull(message = "Store ID can't be null")
    @Positive(message = "Store ID must be positive number")
    @Schema(description = "Store ID", example = "1")
    private Long storeId;

    @NotNull(message = "Quantity can't be null")
    @Positive(message = "Quantity must be positive number")
    @Schema(description = "Ordered product quantity", example = "10")
    private Integer quantity;
}
