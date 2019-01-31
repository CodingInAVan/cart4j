package com.cart4j.model.product.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoV1 {
    private Long id;

    private String sku;

    private String name;

    private String barcode;

    private String mainImage;

    private Long vendorId;

    private Long taxClassId;

    private ProductDescriptionDtoV1 productDescription;

    private ProductSizeDtoV1 productSize;

    private ProductWeightDtoV1 productWeight;

}
