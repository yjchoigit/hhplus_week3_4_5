package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
