package com.hhplus.hhplus_week3_4_5.ecommerce.facade.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.GetProductApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class ProductStockFacade {
    private final ProductService productService;
    private final ProductStockService productStockService;

    public GetProductApiResDto findProductInfo(Long productId){
        // 상품 조회
        Product product = productService.findProductByProductId(productId);

        if(ProductEnums.Type.SINGLE.equals(product.getType())){ // 단일 상품인 경우
            ProductStock productStock = productStockService.findProductStockByProductIdAndProductOptionId(productId, null);
            return null;
        }

        // 상품 옵션 조회
        List<ProductOption> productOptionList = productService.findProductOptionListByProductId(productId);

        // 상품 재고 조회
        for(ProductOption productOption : productOptionList){
            Long productOptionId = productOption.getProductOptionId();
            ProductStock productStock = productStockService.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
        }

        return null;
    }
}
