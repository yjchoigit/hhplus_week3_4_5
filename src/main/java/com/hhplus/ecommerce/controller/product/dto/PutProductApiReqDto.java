package com.hhplus.ecommerce.controller.product.dto;

import com.hhplus.ecommerce.service.product.dto.PutProductReqDto;
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

        public PutProductReqDto request() {
                return new PutProductReqDto(name, price, useYn);
        }
}
