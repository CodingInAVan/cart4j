package com.cart4j.product.service.v1.impl;

import com.cart4j.product.dto.v1.ProductDtoV1;
import com.cart4j.product.entity.Product;
import com.cart4j.product.repository.ProductRepository;
import com.cart4j.product.service.v1.ProductServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImplV1 implements ProductServiceV1 {
    @Autowired private ProductRepository productRepository;

    @Override
    public Page<ProductDtoV1> getList(Pageable pageable) {
        Specification<Product> spec = ProductSpec.activated();
        return productRepository.findAll(spec, pageable).map(ProductDtoV1::from);
    }

    public static class ProductSpec {
        public static Specification<Product> activated() {
            return (root, query, builder) -> builder.equal(root.get("activated"), true);
        }

    }
}
