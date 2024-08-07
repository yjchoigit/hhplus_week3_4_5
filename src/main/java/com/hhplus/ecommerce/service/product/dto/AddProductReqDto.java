package com.hhplus.ecommerce.service.product.dto;

import com.hhplus.ecommerce.domain.product.ProductEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record AddProductReqDto(
        String name,
        ProductEnums.Type type,
        int price,
        boolean useYn,
        List<AddProductOptionReqDto> optionList

) implements Serializable {

    @Builder
    public record AddProductOptionReqDto(
        ProductEnums.OptionType optionType,
        String name,
        String value,
        int price,
        boolean useYn
    ) implements Serializable {
    }
}
