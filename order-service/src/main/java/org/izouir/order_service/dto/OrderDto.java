package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@Schema(description = "Order")
public class OrderDto {
    @Schema(description = "Order ID", example = "1")
    private Long id;

    @Schema(description = "User ID made an order", example = "1")
    private Long userId;

    @Schema(description = "Order total price", example = "100")
    private Integer totalPrice;

    @Schema(description = "Order status", example = "STATUS_CREATED")
    private String status;

    @Schema(description = "Order date", example = "2024-10-10 01:02:03.123456789")
    private Timestamp date;

    @Schema(description = "Order positions")
    private List<OrderPositionDto> positions;
}
