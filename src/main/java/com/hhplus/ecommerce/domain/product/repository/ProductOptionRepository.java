package com.hhplus.ecommerce.domain.product.repository;

import com.hhplus.ecommerce.domain.product.entity.ProductOption;

import java.util.List;

public interface ProductOptionRepository {
    List<ProductOption> findByProductId(Long productId);
    ProductOption findProductOptionByProductIdAndProductOptionId(Long productId, Long productOptionId);
    ProductOption save(ProductOption productOption);
}
