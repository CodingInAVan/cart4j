package com.cart4j.product.dto.v1;

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

    private Integer quantity;

    private String mainImage;

    private Long vendorId;

    private Integer points;

    private Long taxClassId;

    private Long availableAt;
}
