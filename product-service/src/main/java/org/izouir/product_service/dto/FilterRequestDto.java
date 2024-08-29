package org.izouir.product_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Search request filter")
public class FilterRequestDto {
    @Schema(description = "Filter column name", example = "totalPrice")
    private String column;

    @Schema(description = "Filter column value", example = "10")
    private String value;
}
