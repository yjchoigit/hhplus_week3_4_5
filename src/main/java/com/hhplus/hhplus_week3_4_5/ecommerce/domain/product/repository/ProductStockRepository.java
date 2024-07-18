package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;

public interface ProductStockRepository {
    ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId);
    ProductStock save(ProductStock productStock);
}
