package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Запрос на изменение количества хранимого товара")
public class ChangeAmountRequestDto {
    @Schema(description = "Идентификатор хранимого продукта", example = "1")
    private Long storedProductId;

    @Schema(description = "Количество", example = "10")
    private Integer amount;
}
