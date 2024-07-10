package com.hhplus.hhplus_week3_4_5.ecommerce.application.controllers.product.dtos;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record GetProductApiResDto(
        @Schema(description = "상품 ID")
        Long productId,
        @Schema(description = "상품 명")
        String name,
        @Schema(description = "상품 타입 Enum")
        ProductEnums.Type type,
        @Schema(description = "상품 가격")
        BigDecimal price,
        @Schema(description = "상품 옵션 리스트")
        GetProductOptionApiResDto option

) implements Serializable {

    public record GetProductOptionApiResDto(
        @Schema(description = "상품 옵션 타입 Enum")
        ProductEnums.OptionType optionType,
        @Schema(description = "상품 옵션 명")
        String name,
        @Schema(description = "상품 옵션 값 리스트")
        List<GetProductOptionValueApiResDto> list
    ) implements Serializable {

        public record GetProductOptionValueApiResDto(
                @Schema(description = "상품 옵션 ID")
                Long productOptionId,
                @Schema(description = "상품 옵션 값")
                String value,
                @Schema(description = "상품 옵션 가격")
                BigDecimal price
        ) implements Serializable {

        }
    }
}
