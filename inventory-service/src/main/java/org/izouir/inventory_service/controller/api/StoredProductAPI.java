package org.izouir.inventory_service.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.izouir.inventory_service.dto.ChangeAmountRequestDto;
import org.izouir.store_manager_entities.entity.InventoryOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/stored-product")
@Tag(name = "Stored product management")
public interface StoredProductAPI {
    @Operation(summary = "Change quantity of stored product to the store")
    @PostMapping("/change/{operation}")
    ResponseEntity<Void> changeAmount(@RequestBody final ChangeAmountRequestDto request,
                                      @PathVariable("operation") final InventoryOperation operation);
}
