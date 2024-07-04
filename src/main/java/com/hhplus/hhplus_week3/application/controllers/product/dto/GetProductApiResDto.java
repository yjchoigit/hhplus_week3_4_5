package com.hhplus.hhplus_week3.application.controllers.product.dto;

import com.hhplus.hhplus_week3.application.domain.product.ProductEnums;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record GetProductApiResDto(
        Long productId,
        String name,
        ProductEnums.Type type,
        BigDecimal price,
        GetProductOptionApiResDto option

) implements Serializable {

    public record GetProductOptionApiResDto(
        ProductEnums.OptionType optionType,
        String name,
        List<GetProductOptionValueApiResDto> list
    ) implements Serializable {

        public record GetProductOptionValueApiResDto(
                Long productOptionId,
                String value,
                BigDecimal price
        ) implements Serializable {

        }
    }
}
