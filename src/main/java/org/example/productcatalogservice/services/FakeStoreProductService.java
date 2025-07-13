package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class FakeStoreProductService implements IProductService{
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    /**
     *
     * Here service is our internal layer..
     * so return type should be internal -- which is our entity in this case and
     * not the DTO, coz DTO is something that is used for communication, and not internal to our service:
     *  ProductDTO is used to communicate with our client
     *  FakestoreDTO is used to communicate with fakestore
     *
     */
    @Override
    public Product getProductById(Long id){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto = restTemplate.
                getForEntity("https://fakestoreapi.com/products/{id}",
                FakeStoreProductDto.class, id);

        if(fakeStoreProductDto.getBody() != null &&
                fakeStoreProductDto.
                        getStatusCode().
                        equals(HttpStatusCode.valueOf(200))
        ){
            return from(fakeStoreProductDto.getBody());
        }
        return null;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImageUrl());

        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }
}
