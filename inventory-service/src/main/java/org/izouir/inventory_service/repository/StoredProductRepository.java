package org.izouir.inventory_service.repository;

import org.izouir.shared_lib.entity.StoredProduct;
import org.izouir.shared_lib.entity.StoredProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredProductRepository extends JpaRepository<StoredProduct, StoredProductKey> {
}
