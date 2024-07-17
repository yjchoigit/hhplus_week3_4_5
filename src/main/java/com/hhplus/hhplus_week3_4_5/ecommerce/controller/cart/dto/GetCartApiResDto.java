package com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entity.Cart;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record GetCartApiResDto(
    @Schema(description = "장바구니 ID")
    Long cartId,
    @Schema(description = "상품 ID")
    Long productId,
    @Schema(description = "상품 옵션 ID")
    Long productOptionId,
    @Schema(description = "구매 수량")
    int buyCnt
) implements Serializable {
    public static GetCartApiResDto from(Cart cart){
        return new GetCartApiResDto(cart.getCartId(), cart.getProductId(), cart.getProductOptionId(), cart.getBuyCnt());
    }
}
