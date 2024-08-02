package com.hhplus.ecommerce.service.product;

import com.hhplus.ecommerce.domain.product.entity.ProductStock;

public interface ProductStockService {
    ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId);
    boolean deductProductStock(Long productId, Long productOptionId, int buyCnt);
}
