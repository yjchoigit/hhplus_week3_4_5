package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.exception.ProductCustomException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class ProductStockServiceImpl implements ProductStockService {
    private ProductStockRepository productStockRepository;

    @Override
    public ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId) {
        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
        if(productStock == null) {
            throw new ProductCustomException(ProductEnums.Error.NO_PRODUCT_STOCK);
        }

        return productStock;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean deductProductStock(Long productId, Long productOptionId, int buyCnt) {
        // 상품 재고 조회
        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
        if(productStock == null) {
            throw new ProductCustomException(ProductEnums.Error.NO_PRODUCT_STOCK);
        }
        // 상품 재고 valid
        productStock.validate(buyCnt);

        // 상품 재고 차감 처리
        productStock.deduct(buyCnt);

        // 재고 업데이트하면서 낙관적 락 사용
        productStockRepository.save(productStock);

        return true;
    }

}
