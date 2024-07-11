package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {
    private ProductStockRepository productStockRepository;

    @Override
    public ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId) {
        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
        if(productStock == null) {
            throw new IllegalArgumentException("상품 재고 정보가 없습니다.");
        }

        return productStock;
    }

    @Override
    public boolean deductProductStock(Long productId, Long productOptionId, int buyCnt) {
        // 상품 재고 조회
        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
        if(productStock == null) {
            throw new IllegalArgumentException("상품 재고 정보가 없습니다.");
        }
        // 상품 재고 valid
        productStock.validate(buyCnt);

        // 상품 재고 차감 처리
        productStock.deduct(buyCnt);
        return true;
    }

}
