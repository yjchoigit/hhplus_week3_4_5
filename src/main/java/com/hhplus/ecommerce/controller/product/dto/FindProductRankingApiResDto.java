package com.hhplus.ecommerce.controller.product.dto;

import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record FindProductRankingApiResDto(
        @Schema(description = "상품 ID")
        Long productId,
        @Schema(description = "상품 명")
        String name,
        @Schema(description = "상품 타입 Enum")
        ProductEnums.Type type,
        @Schema(description = "상품 가격")
        int price,
        @Schema(description = "총 주문 구매수량")
        int totalBuyCnt
) implements Serializable {
        public static FindProductRankingApiResDto from(Product product, int totalBuyCnt){
                return new FindProductRankingApiResDto(product.getProductId(), product.getName(),
                        product.getType(), product.getPrice(), totalBuyCnt);
        }
}
