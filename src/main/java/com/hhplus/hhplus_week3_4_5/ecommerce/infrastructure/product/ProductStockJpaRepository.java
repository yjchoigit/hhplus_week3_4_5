package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entities.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockJpaRepository extends JpaRepository<ProductStock, Long> {
}
