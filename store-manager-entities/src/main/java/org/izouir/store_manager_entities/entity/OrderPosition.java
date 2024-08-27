package org.izouir.store_manager_entities.entity;

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
@Table(name = "order_position")
public class OrderPosition {
    @EmbeddedId
    private OrderPositionKey id;

    @Column(name = "quantity", columnDefinition = "INT CHECK ( quantity > 0)")
    private Integer quantity;
}
