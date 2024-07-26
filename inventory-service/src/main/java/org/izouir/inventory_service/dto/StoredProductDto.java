package org.izouir.inventory_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Stored product")
public class StoredProductDto {
    @Valid
    @Schema(description = "Product")
    private ProductDto product;

    @NotNull(message = "Store ID can't be null")
    @Positive(message = "Store ID must be equal or greater than 1")
    @Schema(description = "Store ID", example = "1")
    private Long storeId;

    @NotNull(message = "Product count can't be null")
    @Positive(message = "Product count must be equal or greater than 0")
    @Schema(description = "Product count left in store", example = "10")
    private Integer quantity;
}
