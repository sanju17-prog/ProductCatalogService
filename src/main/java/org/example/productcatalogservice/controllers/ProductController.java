package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Iphone 16");
        return product;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            Product product = new Product();
            product.setId((long)index + 1);
            product.setName("Iphone " + index);
            products.add(product);
        }
        return products;
    }

    @PatchMapping("/products/{id}")
    public ProductDto updateProductDetails(@PathVariable Long id, @RequestBody ProductDto productDto) {
        productDto.setId(id);
        productDto.setName("Iphone " + id);
        return productDto;
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        ProductDto productDto1 = new ProductDto();
        productDto1.setName(productDto.getName());
        productDto1.setPrice(productDto.getPrice());
        return productDto1;
    }

    @DeleteMapping("/products/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return true;
    }
}
