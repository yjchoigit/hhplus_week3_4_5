package com.hhplus.hhplus_week3_4_5.application.controllers.product.dto;

import com.hhplus.hhplus_week3_4_5.application.domain.product.ProductEnums;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record AddProductApiReqDto(
        String name,
        ProductEnums.Type type,
        BigDecimal price,
        boolean useYn,
        AddProductOptionApiReqDto option

) implements Serializable {

    public record AddProductOptionApiReqDto(
        ProductEnums.OptionType optionType,
        String name,
        List<AddProductOptionValueApiReqDto> list
    ) implements Serializable {

        public record AddProductOptionValueApiReqDto(
                String value,
                BigDecimal price,
                boolean useYn
        ) implements Serializable {
            
        }
    }
}
