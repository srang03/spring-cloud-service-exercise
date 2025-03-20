package org.example.catalogservice.controller;

import org.example.catalogservice.model.CatalogDto;
import org.example.catalogservice.model.ResponseCatalog;
import org.example.catalogservice.service.CatalogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
    Environment environment;
    CatalogService catalogService;

    @Autowired
    public CatalogController(Environment environment, CatalogService catalogService) {
        this.environment = environment;
        this.catalogService = catalogService;
    }

    @GetMapping("/catalogs")
    public List<ResponseCatalog> getAllCatalog(){

        Iterable<CatalogDto> catalogDtos = catalogService.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<ResponseCatalog> responseCatalogs = new ArrayList<>();
        for (CatalogDto catalogDto : catalogDtos) {
            ResponseCatalog responseCatalog = modelMapper.map(catalogDto, ResponseCatalog.class);
            responseCatalogs.add(responseCatalog);
        }
        return responseCatalogs;
    }

    @GetMapping("/catalogs/{product_id}")
    public ResponseCatalog getCatalogByProductId(@PathVariable String product_id){
        CatalogDto catalogDto = catalogService.retrieveProductId(product_id);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(catalogDto, ResponseCatalog.class);
    }

}
