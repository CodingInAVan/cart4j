package com.cart4j.product.service.v1;

import com.cart4j.model.product.dto.v1.ProductDtoV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductServiceV1 {
    Page<ProductDtoV1> getList(Pageable pageable);
}
