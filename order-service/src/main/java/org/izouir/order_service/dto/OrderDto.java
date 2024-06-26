package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@Schema(description = "Заказ")
public class OrderDto {
    @Schema(description = "Идентификатор заказа", example = "1")
    private Long id;

    @Schema(description = "Заказываемый продукт")
    private StoredProductDto storedProduct;

    @Schema(description = "Статус заказа", example = "STATUS_CREATED")
    private String status;

    @Schema(description = "Заказываемое количество", example = "10")
    private Integer quantity;

    @Schema(description = "Время размещения заказа", example = "2024-10-10 01:02:03.123456789")
    private Timestamp placedAt;
}
