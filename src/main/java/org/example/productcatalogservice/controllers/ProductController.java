package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<FakeStoreProductDto> getProducts() {
        List<Product> products = productService.getAllProducts();
        List<FakeStoreProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            productDtos.add(from(product));
        }
        return productDtos;
    }

    @GetMapping("{id}")
    public FakeStoreProductDto getProductDetails(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if(product == null) {
            return null;
        }
        return from(product);
    }

    @PutMapping("{id}")
    public FakeStoreProductDto updateProductDetails(@PathVariable Long id, @RequestBody FakeStoreProductDto fakeStoreProductDto) {
        Product product = productService.updateProduct(fakeStoreProductDto);
        if(product == null) {
            return null;
        }
        return fakeStoreProductDto;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImageUrl(product.getImageUrl());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        return fakeStoreProductDto;
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
