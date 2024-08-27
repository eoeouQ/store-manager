package org.izouir.inventory_service.repository;

import org.izouir.store_manager_entities.entity.StoredProduct;
import org.izouir.store_manager_entities.entity.StoredProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredProductRepository extends JpaRepository<StoredProduct, StoredProductKey> {
}
