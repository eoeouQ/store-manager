package org.izouir.order_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Request of searching orders by filters")
public class FiltersRequestDto {
    @Schema(description = "List of filters")
    private List<FilterRequestDto> filters;
}
