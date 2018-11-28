package com.cart4j.product.service.impl;

import com.cart4j.product.repository.ProductRepository;
import com.cart4j.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired private ProductRepository productRepository;
}
