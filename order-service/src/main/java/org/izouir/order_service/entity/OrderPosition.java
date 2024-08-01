package org.izouir.order_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "order_position")
public class OrderPosition {
    @EmbeddedId
    OrderPositionKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT REFERENCES order (id)")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", columnDefinition = "BIGINT REFERENCES product (id)")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", columnDefinition = "BIGINT REFERENCES store (id)")
    private Store store;

    @Column(name = "quantity", columnDefinition = "INT CHECK ( quantity > 0)")
    private Integer quantity;
}
