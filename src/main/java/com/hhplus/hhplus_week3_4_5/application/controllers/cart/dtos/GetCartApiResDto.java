package com.hhplus.hhplus_week3_4_5.application.controllers.cart.dtos;

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
}
