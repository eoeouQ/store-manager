package org.izouir.inventory_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@Schema(description = "Error")
public class ErrorDto {
    @Schema(description = "Error code", example = "500")
    private int code;

    @Schema(description = "Error message", example = "The request parameter {parameterName} is missing...")
    private String message;

    @Schema(description = "Error timestamp", example = "2021-03-24 16:34:26.666")
    private Timestamp timestamp;
}
