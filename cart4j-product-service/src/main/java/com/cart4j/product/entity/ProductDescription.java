package com.cart4j.product.entity;

import com.cart4j.model.product.dto.v1.ProductDescriptionDtoV1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name="c4_product_description")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String metaTitle;

    private String metaDescription;

    private String metaKeyword;

    public ProductDescriptionDtoV1 toDtoV1() {
        return ProductDescriptionDtoV1.builder()
                .description(description)
                .id(id)
                .metaDescription(metaDescription)
                .metaKeyword(metaKeyword)
                .metaTitle(metaTitle)
                .build();
    }
}
