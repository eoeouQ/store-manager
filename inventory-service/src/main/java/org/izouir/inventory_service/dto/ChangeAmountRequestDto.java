package org.izouir.inventory_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request of changing stored product quantity")
public class ChangeAmountRequestDto {
    @NotNull(message = "Product ID can't be null")
    @Positive(message = "Product ID must be equal or greater than 1")
    @Schema(description = "Product ID", example = "1")
    private Long productId;

    @NotNull(message = "Store ID can't be null")
    @Positive(message = "Store ID must be equal or greater than 1")
    @Schema(description = "Store ID", example = "1")
    private Long storeId;

    @NotNull(message = "Amount can't be null")
    @Positive(message = "Amount must be equal or greater than 0")
    @Schema(description = "Amount of change", example = "10")
    private Integer amount;
}
