package com.hhplus.ecommerce.facade.product.dto;

import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record FindProductRankingResDto(
        Long productId,
        String name,
        ProductEnums.Type type,
        int price,
        int totalBuyCnt
) implements Serializable {
        public static FindProductRankingResDto from(Product product, int totalBuyCnt){
                return new FindProductRankingResDto(product.getProductId(), product.getName(),
                        product.getType(), product.getPrice(), totalBuyCnt);
        }
}
