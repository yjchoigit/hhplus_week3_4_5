package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import jakarta.persistence.EntityNotFoundException;
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
        return productJpaRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }
}
