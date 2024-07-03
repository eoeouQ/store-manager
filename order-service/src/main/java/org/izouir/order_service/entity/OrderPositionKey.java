package org.izouir.order_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderPositionKey {
    @Column(name = "stored_product_id", columnDefinition = "BIGINT REFERENCES stored_product (id)")
    Long storedProductId;

    @Column(name = "order_id", columnDefinition = "BIGINT REFERENCES order (id)")
    Long orderId;
}
