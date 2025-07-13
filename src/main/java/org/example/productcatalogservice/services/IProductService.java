package org.example.productcatalogservice.services;

import org.example.productcatalogservice.models.Product;


public interface IProductService {
    Product getProductById(Long id);
}
