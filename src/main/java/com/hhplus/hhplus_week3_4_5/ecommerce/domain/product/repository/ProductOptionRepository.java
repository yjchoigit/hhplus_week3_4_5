package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;

import java.util.List;

public interface ProductOptionRepository {
    List<ProductOption> findByProductId(Long productId);
    ProductOption findProductOptionByProductIdAndProductOptionId(Long productId, Long productOptionId);
    ProductOption save(ProductOption productOption);
}
