package com.hhplus.hhplus_week3_4_5.ecommerce.facade.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.FindProductApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductStockFacade {
    private final ProductService productService;
    private final ProductStockService productStockService;

    public FindProductApiResDto findProduct(Long productId){
        // 상품 조회
        Product product = productService.findProductByProductId(productId);

        // 단일 상품인 경우
        if(ProductEnums.Type.SINGLE.equals(product.getType())){
            // 상품 재고 조회
            ProductStock productStock = productStockService.findProductStockByProductIdAndProductOptionId(productId, null);
            return FindProductApiResDto.fromSingleProduct(product, productStock);
        }
        
        // 옵션 상품인 경우
        // 상품 옵션 조회
        List<ProductOption> productOptionList = productService.findProductOptionListByProductId(productId);
        List<FindProductApiResDto.FindProductOptionApiResDto> optionList = new ArrayList<>();

        // 상품 재고 조회
        for(ProductOption productOption : productOptionList){
            Long productOptionId = productOption.getProductOptionId();
            ProductStock productStock = productStockService.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
            optionList.add(FindProductApiResDto.FindProductOptionApiResDto.fromOptions(productOption, productStock));
        }

        return FindProductApiResDto.fromOptionProduct(product, optionList);
    }
}
