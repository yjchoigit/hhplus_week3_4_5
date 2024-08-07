package com.hhplus.ecommerce.facade.product;

import com.hhplus.ecommerce.controller.product.dto.FindProductApiResDto;
import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.ecommerce.facade.product.dto.FindProductResDto;
import com.hhplus.ecommerce.service.product.ProductService;
import com.hhplus.ecommerce.service.product.ProductStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductStockFacade {
    private final ProductService productService;
    private final ProductStockService productStockService;

    public FindProductResDto findProduct(Long productId){
        // 상품 조회
        Product product = productService.findProductByProductId(productId);

        // 단일 상품인 경우
        if(ProductEnums.Type.SINGLE.equals(product.getType())){
            // 상품 재고 조회
            ProductStock productStock = productStockService.findProductStockByProductIdAndProductOptionId(productId, null);
            return FindProductResDto.fromSingleProduct(product, productStock);
        }
        
        // 옵션 상품인 경우
        // 상품 옵션 조회
        List<ProductOption> productOptionList = productService.findProductOptionListByProductId(productId);
        List<FindProductResDto.FindProductOptionResDto> optionList = new ArrayList<>();

        // 상품 재고 조회
        for(ProductOption productOption : productOptionList){
            Long productOptionId = productOption.getProductOptionId();
            ProductStock productStock = productStockService.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
            optionList.add(FindProductResDto.FindProductOptionResDto.fromOptions(productOption, productStock));
        }

        return FindProductResDto.fromOptionProduct(product, optionList);
    }
}
