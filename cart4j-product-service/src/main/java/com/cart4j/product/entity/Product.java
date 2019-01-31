package com.cart4j.product.entity;

import com.cart4j.model.product.dto.v1.ProductDtoV1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name="c4_product")
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private String name;

    private String barcode;

    private String mainImage;

    private Long vendorId;

    private Long taxClassId;

    @OneToOne
    @JoinColumn(name="product_description_id")
    private ProductDescription productDescription;

    @OneToOne
    @JoinColumn(name="product_size_id")
    private ProductSize productSize;

    @OneToOne
    @JoinColumn(name="weight_id")
    private ProductWeight productWeight;

    private Boolean activated;

    public ProductDtoV1 toDtoV1() {
        return ProductDtoV1.builder()
                .barcode(barcode)
                .id(id)
                .mainImage(mainImage)
                .name(name)
                .productDescription(productDescription.toDtoV1())
                .productSize(productSize.toDtoV1())
                .productWeight(productWeight.toDtoV1())
                .sku(sku)
                .taxClassId(taxClassId)
                .vendorId(vendorId)
                .build();
    }
}
