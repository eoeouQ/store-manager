package org.izouir.order_service.repository;

import org.izouir.order_service.entity.Order;
import org.izouir.order_service.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByDateAsc();

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByTotalPrice(Integer totalPrice);

    List<Order> findAllByStatus(OrderStatus status);

    List<Order> findAllByDate(Timestamp date);
}
