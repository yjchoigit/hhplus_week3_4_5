package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entities.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOption, Long> {
}
