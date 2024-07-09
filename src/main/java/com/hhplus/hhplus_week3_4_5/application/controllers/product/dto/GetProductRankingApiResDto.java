package com.hhplus.hhplus_week3_4_5.application.controllers.product.dto;

import java.io.Serializable;

public record GetProductRankingApiResDto(
        Long productId,
        String name
) implements Serializable {
}
