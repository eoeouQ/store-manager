package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Request of placing new order")
public class PlaceOrderRequestDto {
    @NotNull(message = "User ID can't be null")
    @Positive(message = "User ID must be positive number")
    @Schema(description = "User ID made an order", example = "1")
    private Long userId;

    @Valid
    @Schema(description = "Order positions")
    private List<OrderPositionDto> positions;
}
