package org.izouir.store_manager_entities.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_seq")
    @SequenceGenerator(name = "store_seq", sequenceName = "store_sequence", allocationSize = 1)
    @Column(name = "id", columnDefinition = "BIGINT PRIMARY KEY")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(64) NOT NULL")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "location", columnDefinition = "VARCHAR(64) NOT NULL")
    private StoreLocation location;
}
