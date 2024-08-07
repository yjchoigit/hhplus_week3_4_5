package com.hhplus.ecommerce.domain.product.repository;

import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOption, Long> {
    List<ProductOption> findAllByProduct_productId(Long productId);
    ProductOption findProductOptionByProduct_productIdAndProductOptionId(Long productId, Long productOptionId);
}
