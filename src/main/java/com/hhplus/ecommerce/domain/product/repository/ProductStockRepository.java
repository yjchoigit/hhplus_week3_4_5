package com.hhplus.ecommerce.domain.product.repository;

import com.hhplus.ecommerce.domain.product.entity.ProductStock;

public interface ProductStockRepository {
    ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId);
    ProductStock save(ProductStock productStock);
}
