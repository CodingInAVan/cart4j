package com.cart4j.product.dto.v1;

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
}
