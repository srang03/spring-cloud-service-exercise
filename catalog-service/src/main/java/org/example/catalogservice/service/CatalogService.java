package org.example.catalogservice.service;

import org.example.catalogservice.model.CatalogDto;
import org.example.catalogservice.model.CatalogEntity;
import org.springframework.stereotype.Service;

@Service
public interface CatalogService {
    public Iterable<CatalogDto> findAll();
    public CatalogDto retrieveProductId(String productId);
}
