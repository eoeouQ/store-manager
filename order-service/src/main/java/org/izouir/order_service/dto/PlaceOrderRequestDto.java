package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Запрос на размещение заказа")
public class PlaceOrderRequestDto {
    @Schema(description = "Идентификатор заказываемого продукта", example = "1")
    private Long storedProductId;

    @Schema(description = "Количество продукта", example = "10")
    private Integer quantity;
}
