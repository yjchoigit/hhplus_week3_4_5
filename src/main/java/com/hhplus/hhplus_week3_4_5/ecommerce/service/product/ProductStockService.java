package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;

public interface ProductStockService {
    ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId);
    boolean deductProductStock(Long productId, Long productOptionId, int buyCnt);
}
