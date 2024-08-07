package com.hhplus.ecommerce.controller.product.dto;

import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.service.product.dto.AddProductReqDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

public record AddProductApiReqDto(
        @Schema(description = "상품 명")
        String name,
        @Schema(description = "상품 타입 Enum")
        ProductEnums.Type type,
        @Schema(description = "상품 가격")
        int price,
        @Schema(description = "사용 여부")
        boolean useYn,
        @Schema(description = "상품 옵션 리스트")
        List<AddProductOptionApiReqDto> optionList

) implements Serializable {

    public AddProductReqDto request() {
        return AddProductReqDto.builder()
                .name(name)
                .type(type)
                .price(price)
                .useYn(useYn)
                .optionList(optionList.stream().map(AddProductOptionApiReqDto::request).toList())
                .build();
    }

    public record AddProductOptionApiReqDto(
        @Schema(description = "상품 옵션 타입 Enum")
        ProductEnums.OptionType optionType,
        @Schema(description = "상품 옵션 명")
        String name,
        @Schema(description = "상품 옵션 값")
        String value,
        @Schema(description = "상품 옵션 가격")
        int price,
        @Schema(description = "사용 여부")
        boolean useYn
    ) implements Serializable {

        public AddProductReqDto.AddProductOptionReqDto request() {
            return AddProductReqDto.AddProductOptionReqDto.builder()
                    .optionType(optionType)
                    .name(name)
                    .value(value)
                    .price(price)
                    .useYn(useYn)
                    .build();
        }

    }
}
