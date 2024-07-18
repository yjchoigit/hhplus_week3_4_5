package com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record FindProductListApiResDto(
        @Schema(description = "상품 ID")
        Long productId,
        @Schema(description = "상품 명")
        String name,
        @Schema(description = "상품 타입 Enum")
        ProductEnums.Type type,
        @Schema(description = "상품 가격")
        int price
) implements Serializable {
    public static FindProductListApiResDto from(Product product){
        return new FindProductListApiResDto(product.getProductId(), product.getName(),
                product.getType(), product.getPrice());
    }
}

