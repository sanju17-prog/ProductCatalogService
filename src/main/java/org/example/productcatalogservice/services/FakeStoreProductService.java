package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public Product updateProduct(FakeStoreProductDto fakeStoreProductDto){
        if (fakeStoreProductDto.getId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null for update.");
        }
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                this.putForEntity("https://fakestoreapi.com/products/{id}",
                        HttpMethod.PUT,
                        fakeStoreProductDto,
                        FakeStoreProductDto.class,
                        fakeStoreProductDto.getId());

        if(fakeStoreProductDtoResponseEntity.getBody() != null &&
        fakeStoreProductDtoResponseEntity.
                getStatusCode().
                is2xxSuccessful()
        ){
            Product product = from(fakeStoreProductDtoResponseEntity.getBody());
            product.setId(fakeStoreProductDto.getId());
            return product;
        }
        return null;
    }

    public <T> ResponseEntity<T> putForEntity(String url,
                                              HttpMethod httpMethod,
                                              @Nullable Object request,
                                              Class<T> responseType,
                                              Object... uriVariables
    ) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    @Override
    public List<Product> getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class);

        List<Product> products = new ArrayList<>();
        assert fakeStoreProductDtoResponseEntity.getBody() != null;
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtoResponseEntity.getBody()){
            Product product = from(fakeStoreProductDto);
            products.add(product);
        }
        return products;
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
