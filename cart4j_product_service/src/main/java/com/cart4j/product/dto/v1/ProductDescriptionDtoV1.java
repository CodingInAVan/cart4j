package com.cart4j.product.dto.v1;

import com.cart4j.product.entity.ProductDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDescriptionDtoV1 {
    private Long id;

    private String description;

    private String metaTitle;

    private String metaDescription;

    private String metaKeyword;

    public static ProductDescriptionDtoV1 from(ProductDescription productDescription) {
        return ProductDescriptionDtoV1.builder()
                .description(productDescription.getDescription())
                .id(productDescription.getId())
                .metaDescription(productDescription.getMetaDescription())
                .metaKeyword(productDescription.getMetaKeyword())
                .metaTitle(productDescription.getMetaTitle())
                .build();
    }
}
