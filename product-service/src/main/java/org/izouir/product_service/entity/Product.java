package org.izouir.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGSERIAL PRIMARY KEY")
    private Long id;

    @Column(name = "label", columnDefinition = "VARCHAR(64) UNIQUE NOT NULL")
    private String label;

    @Column(name = "price", columnDefinition = "INT NOT NULL CHECK ( price > 0 )")
    private Integer price;
}
