package com.hhplus.ecommerce.service.product;

import com.hhplus.ecommerce.domain.product.entity.ProductStock;

public interface ProductStockService {
    // 상품 id, 상품 옵션 id로 상품 재고 조회
    ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId);
    
    // 상품 재고 차감
    ProductStock deductProductStock(Long productId, Long productOptionId, int buyCnt);
}
