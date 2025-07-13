package org.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductDto extends BaseDto {
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
}