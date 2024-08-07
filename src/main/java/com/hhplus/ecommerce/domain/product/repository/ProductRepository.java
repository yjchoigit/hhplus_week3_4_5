package com.hhplus.ecommerce.domain.product.repository;

import com.hhplus.ecommerce.domain.product.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findProductList();
    Product findByProductId(Long productId);
    Product save(Product product);
}
