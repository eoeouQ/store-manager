package org.izouir.order_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", allocationSize = 1)
    @Column(name = "id", columnDefinition = "BIGINT PRIMARY KEY")
    private Long id;

    @Column(name = "user_id", columnDefinition = "BIGINT NOT NULL")
    private Long userId;

    @Column(name = "total_price", columnDefinition = "INT NOT NULL")
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(32) NOT NULL")
    private OrderStatus status;

    @Column(name = "date", columnDefinition = "TIMESTAMP NOT NULL DEFAULT NOW()")
    private Timestamp date;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT REFERENCES order (id)")
    private List<OrderPosition> positions;
}
