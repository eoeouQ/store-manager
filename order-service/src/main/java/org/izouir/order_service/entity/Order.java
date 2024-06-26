package org.izouir.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGSERIAL PRIMARY KEY")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stored_product_id", columnDefinition = "BIGINT REFERENCES stored_products (id)")
    private StoredProduct storedProduct;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(32) NOT NULL")
    private OrderStatus status;

    @Column(name = "quantity", columnDefinition = "INTEGER CHECK ( quantity > 0)")
    private Integer quantity;

    @Column(name = "placed_at", columnDefinition = "TIMESTAMP NOT NULL DEFAULT NOW()")
    private Timestamp placedAt;
}
