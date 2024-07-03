package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Request of placing new order")
public class PlaceOrderRequestDto {
    @Schema(description = "User ID made an order", example = "1")
    private Long userId;

    @Schema(description = "Order positions")
    private List<OrderPositionDto> positions;
}
