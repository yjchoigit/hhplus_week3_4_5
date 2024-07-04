package com.hhplus.hhplus_week3.application.controllers.product.dto;

import com.hhplus.hhplus_week3.application.domain.product.ProductEnums;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record PutProductApiReqDto(
        String name,
        ProductEnums.Type type,
        BigDecimal price,
        boolean useYn,
        PutProductOptionApiReqDto option

) implements Serializable {

    public record PutProductOptionApiReqDto(
        ProductEnums.OptionType optionType,
        String name,
        List<PutProductOptionValueApiReqDto> list
    ) implements Serializable {

        public record PutProductOptionValueApiReqDto(
                String value,
                BigDecimal price,
                boolean useYn
        ) implements Serializable {
            
        }
    }
}
