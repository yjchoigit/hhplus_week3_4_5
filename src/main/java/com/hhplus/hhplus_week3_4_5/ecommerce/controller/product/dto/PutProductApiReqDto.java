package com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record PutProductApiReqDto(
        @Schema(description = "상품 명")
        String name,
        @Schema(description = "상품 가격")
        int price,
        @Schema(description = "사용 여부")
        boolean useYn
) implements Serializable {

}
