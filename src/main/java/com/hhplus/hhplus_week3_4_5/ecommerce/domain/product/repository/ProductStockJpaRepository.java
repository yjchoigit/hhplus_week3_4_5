package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface ProductStockJpaRepository extends JpaRepository<ProductStock, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ProductStock findByProduct_ProductId(Long productId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ProductStock findByProduct_ProductIdAndProductOption_ProductOptionId(Long productId, Long productOptionId);
}
