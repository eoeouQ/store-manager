package org.izouir.product_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Product")
public class ProductDto {
    @NotNull(message = "Product ID can't be null")
    @Positive(message = "Product ID must be equal or greater than 1")
    @Schema(description = "Product ID", example = "1")
    private Long id;

    @NotNull(message = "Product label can't be null")
    @NotBlank(message = "Product label can't be blank")
    @Size(min = 3, max = 64, message = "Product label's length must be between 3 and 64")
    @Schema(description = "Product label", example = "Mobile phone")
    private String label;

    @NotNull(message = "Product price can't be null")
    @Positive(message = "Product price must be equal or greater than 1")
    @Schema(description = "Product price", example = "10")
    private Integer price;
}
