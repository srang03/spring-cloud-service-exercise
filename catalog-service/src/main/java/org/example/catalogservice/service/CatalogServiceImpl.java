package org.example.catalogservice.service;

import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.catalogservice.model.CatalogDto;
import org.example.catalogservice.model.CatalogEntity;
import org.example.catalogservice.repository.CatalogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {
    CatalogRepository catalogRepository;

    @Autowired
    public CatalogServiceImpl(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    @Override
    public Iterable<CatalogDto> findAll() {
        Iterable<CatalogEntity> catalogEntities = catalogRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        ArrayList<CatalogDto> catalogDtos = new ArrayList<>();

        for (CatalogEntity catalogEntity : catalogEntities) {
            CatalogDto catalogDto = modelMapper.map(catalogEntity, CatalogDto.class);
            catalogDtos.add(catalogDto);
        }
        return catalogDtos;
    }

    @Override
    public CatalogDto retrieveProductId(String productId) {
        CatalogEntity catalogEntity = catalogRepository.findByProductId(productId);
        if(catalogEntity == null) {
            throw new NotFoundException("catalog not found");
        }

        return new ModelMapper().map(catalogEntity, CatalogDto.class);
    }
}
