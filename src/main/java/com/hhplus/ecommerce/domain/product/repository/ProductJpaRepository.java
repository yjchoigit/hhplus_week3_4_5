package com.hhplus.ecommerce.domain.product.repository;

import com.hhplus.ecommerce.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUseYnTrueAndDelYnFalse();
}
