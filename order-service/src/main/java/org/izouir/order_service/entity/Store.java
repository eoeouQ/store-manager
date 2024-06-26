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
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGSERIAL PRIMARY KEY")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "location", columnDefinition = "VARCHAR(64) NOT NULL")
    private StoreLocation location;
}
