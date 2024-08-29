package org.izouir.store_manager_entities.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"order\"")
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

    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = " ORDER_STATUS NOT NULL")
    private OrderStatus status;

    @Column(name = "date", columnDefinition = "TIMESTAMP NOT NULL DEFAULT NOW()")
    private Timestamp date;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT REFERENCES \"order\" (id)")
    private List<OrderPosition> positions;
}
