package org.izouir.inventory_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Request of changing stored product quantity")
public class ChangeAmountRequestDto {
    @NotNull(message = "Stored product ID can't be null")
    @Positive(message = "Stored product ID must be equal or greater than 1")
    @Schema(description = "Stored product ID", example = "1")
    private Long storedProductId;

    @NotNull(message = "Amount can't be null")
    @Positive(message = "Amount must be equal or greater than 0")
    @Schema(description = "Amount of change", example = "10")
    private Integer amount;
}
