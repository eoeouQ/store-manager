package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Request of changing stored product quantity")
public class ChangeAmountRequestDto {
    @Schema(description = "Stored product ID", example = "1")
    private Long storedProductId;

    @Schema(description = "Amount of change", example = "10")
    private Integer amount;
}
