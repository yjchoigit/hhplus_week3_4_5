package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entities.ProductRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRankJpaRepository extends JpaRepository<ProductRank, Long> {
}
