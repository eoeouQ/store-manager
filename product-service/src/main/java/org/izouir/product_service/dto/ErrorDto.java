package org.izouir.product_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@Schema(description = "Ошибка")
public class ErrorDto {
    @Schema(description = "Код ошибки", example = "500")
    private int code;

    @Schema(description = "Сообщение об ошибке", example = "The request parameter {parameterName} is missing...")
    private String message;

    @Schema(description = "Время возникновения ошибки", example = "2021-03-24 16:34:26.666")
    private Timestamp timestamp;
}
