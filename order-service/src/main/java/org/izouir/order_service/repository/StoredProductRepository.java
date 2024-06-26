package org.izouir.order_service.repository;

import org.izouir.order_service.entity.StoredProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredProductRepository extends JpaRepository<StoredProduct, Long> {
}
