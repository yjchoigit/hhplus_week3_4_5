package com.hhplus.ecommerce.domain.product.repository;

import com.hhplus.ecommerce.domain.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public List<Product> findProductList() {
        return productJpaRepository.findAllByUseYnTrueAndDelYnFalse();
    }

    @Override
    public Product findByProductId(Long productId) {
        return productJpaRepository.findById(productId).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }
}
