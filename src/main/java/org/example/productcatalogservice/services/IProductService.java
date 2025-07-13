package org.example.productcatalogservice.services;

import org.example.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
}
