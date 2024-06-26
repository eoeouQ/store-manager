package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Хранимый продукт")
public class StoredProductDto {
    @Schema(description = "Идентификатор хранимого продукта", example = "1")
    private Long id;

    @Schema(description = "Продукт")
    private ProductDto product;

    @Schema(description = "Идентификатор склада", example = "1")
    private Long storeId;

    @Schema(description = "Остаток на складе", example = "10")
    private Integer stored;
}
