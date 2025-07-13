package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products/{id}")
    public ProductDto getProductDetails(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if(product == null) {
            return null;
        }
        return from(product);
    }

    private ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());

        if(productDto.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(productDto.getCategory().getName());
            categoryDto.setId(productDto.getCategory().getId());
            productDto.setCategory(categoryDto);
        }

        return productDto;
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
    public Product deleteProduct(@PathVariable Long id) {
        System.out.println(id);
        return null;
    }
}
