package com.hhplus.hhplus_week3_4_5.application.controllers.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record GetProductRankingApiResDto(
        @Schema(description = "상품 ID")
        Long productId,
        @Schema(description = "상품 명")
        String name
) implements Serializable {
}
