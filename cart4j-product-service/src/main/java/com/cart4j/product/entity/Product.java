package com.cart4j.product.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
}
