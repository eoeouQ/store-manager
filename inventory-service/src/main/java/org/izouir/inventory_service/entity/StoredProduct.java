package org.izouir.inventory_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stored_product")
public class StoredProduct {
    @EmbeddedId
    private StoredProductKey id;

    @Column(name = "quantity", columnDefinition = "INT CHECK ( quantity >= 0 )")
    private Integer quantity;
}
