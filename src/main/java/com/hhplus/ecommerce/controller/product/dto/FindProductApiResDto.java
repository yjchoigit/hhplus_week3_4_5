package com.hhplus.ecommerce.controller.product.dto;

import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.ecommerce.facade.product.dto.FindProductResDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

public record FindProductApiResDto(
        @Schema(description = "상품 ID")
        Long productId,
        @Schema(description = "상품 명")
        String name,
        @Schema(description = "상품 타입 Enum")
        ProductEnums.Type type,
        @Schema(description = "상품 가격")
        int price,
        @Schema(description = "상품 총 재고")
        int totalStock,
        @Schema(description = "상품 옵션 정보")
        List<FindProductOptionApiResDto> optionList

) implements Serializable {

    public static FindProductApiResDto from(FindProductResDto resDto) {
        return new FindProductApiResDto(resDto.productId(), resDto.name(), resDto.type(),
                resDto.price(), resDto.totalStock(), resDto.optionList().stream().map(FindProductOptionApiResDto::from).toList());
    }

    public record FindProductOptionApiResDto(
            @Schema(description = "상품 옵션 ID")
            Long productOptionId,
            @Schema(description = "상품 옵션 타입 Enum")
            ProductEnums.OptionType optionType,
            @Schema(description = "상품 옵션 명")
            String name,
            @Schema(description = "상품 옵션 값")
            String value,
            @Schema(description = "상품 옵션 가격")
            int price,
            @Schema(description = "상품 재고")
            int stock
    ) implements Serializable {
        public static FindProductOptionApiResDto from(FindProductResDto.FindProductOptionResDto resDto){
            return new FindProductOptionApiResDto(resDto.productOptionId(), resDto.optionType(), resDto.name(),
                    resDto.value(), resDto.price(), resDto.stock());
        }
    }
}
