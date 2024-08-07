package com.hhplus.ecommerce.controller.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public record AddCartApiReqDto(
    @Schema(description = "상품 ID")
    @NotNull
    Long productId,
    @Schema(description = "상품 옵션 ID")
    Long productOptionId,
    @Schema(description = "구매 수량")
    @Positive
    int buyCnt
) implements Serializable {
}
