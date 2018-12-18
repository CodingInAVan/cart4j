package com.cart4j.product.dto.v1;

import com.cart4j.product.entity.Product;
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

    public static ProductDtoV1 from(Product product) {
        return ProductDtoV1.builder()
                .barcode(product.getBarcode())
                .id(product.getId())
                .mainImage(product.getMainImage())
                .name(product.getName())
                .sku(product.getSku())
                .taxClassId(product.getTaxClassId())
                .vendorId(product.getVendorId())
                .productDescription(product.getProductDescription() != null ? ProductDescriptionDtoV1.from(product.getProductDescription()) : null)
                .productSize(product.getProductSize() != null ? ProductSizeDtoV1.from(product.getProductSize()) : null)
                .productWeight(product.getProductWeight() != null ? ProductWeightDtoV1.from(product.getProductWeight()) : null)
                .build();

    }
}
