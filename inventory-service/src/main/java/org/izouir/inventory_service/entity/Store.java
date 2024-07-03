package org.izouir.inventory_service.entity;

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
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGSERIAL PRIMARY KEY")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(64) NOT NULL")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "location", columnDefinition = "VARCHAR(64) NOT NULL")
    private StoreLocation location;
}
