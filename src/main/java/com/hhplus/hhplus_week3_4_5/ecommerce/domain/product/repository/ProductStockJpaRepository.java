package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockJpaRepository extends JpaRepository<ProductStock, Long> {
    ProductStock findByProduct_ProductId(Long productId);
    ProductStock findByProduct_ProductIdAndProductOption_ProductOptionId(Long productId, Long productOptionId);
}
