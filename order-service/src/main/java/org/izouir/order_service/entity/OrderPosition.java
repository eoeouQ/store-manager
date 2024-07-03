package org.izouir.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_position")
public class OrderPosition {
    @EmbeddedId
    OrderPositionKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stored_product_id", columnDefinition = "BIGINT REFERENCES stored_product (id)")
    private StoredProduct storedProduct;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT REFERENCES order (id)")
    private Order order;

    @Column(name = "quantity", columnDefinition = "INT CHECK ( quantity > 0)")
    private Integer quantity;
}
