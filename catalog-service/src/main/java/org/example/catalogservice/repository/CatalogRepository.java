package org.example.catalogservice.repository;

import org.example.catalogservice.model.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    CatalogEntity findByProductName(String productName);
    CatalogEntity findByProductId(String productId);
}
