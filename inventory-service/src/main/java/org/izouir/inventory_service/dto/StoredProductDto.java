package org.izouir.inventory_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Stored product")
public class StoredProductDto {
    @Schema(description = "Stored product ID", example = "1")
    private Long id;

    @Schema(description = "Product")
    private ProductDto product;

    @Schema(description = "Store ID", example = "1")
    private Long storeId;

    @Schema(description = "Product count left in store", example = "10")
    private Integer quantity;
}
