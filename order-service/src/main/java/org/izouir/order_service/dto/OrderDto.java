package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@Schema(description = "Order")
public class OrderDto {
    @NotNull(message = "Order ID can't be null")
    @Positive(message = "Order ID must be positive number")
    @Schema(description = "Order ID", example = "1")
    private Long id;

    @NotNull(message = "User ID can't be null")
    @Positive(message = "User ID must be positive number")
    @Schema(description = "User ID made an order", example = "1")
    private Long userId;

    @NotNull(message = "Total price can't be null")
    @Positive(message = "Total price must be positive number")
    @Schema(description = "Order total price", example = "100")
    private Integer totalPrice;

    @NotNull(message = "Order status can't be null")
    @NotBlank(message = "Order status can't be blank")
    @Schema(description = "Order status", example = "STATUS_CREATED")
    private String status;

    @NotNull(message = "Order date can't be null")
    @PastOrPresent(message = "Order must be placed in the past or at the moment")
    @Schema(description = "Order date", example = "2024-10-10 01:02:03.123456789")
    private Timestamp date;

    @Valid
    @Schema(description = "Order positions")
    private List<OrderPositionDto> positions;
}
