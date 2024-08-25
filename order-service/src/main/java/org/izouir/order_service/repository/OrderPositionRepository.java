package org.izouir.order_service.repository;

import org.izouir.shared_lib.entity.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Long> {
}
