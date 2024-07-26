package org.izouir.inventory_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class StoredProductKey {
    @Column(name = "product_id", columnDefinition = "BIGINT REFERENCES product (id)")
    Long productId;

    @Column(name = "store_id", columnDefinition = "BIGINT REFERENCES store (id)")
    Long storeId;
}
