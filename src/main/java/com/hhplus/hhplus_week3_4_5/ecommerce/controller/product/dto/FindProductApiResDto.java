package com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
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
        public static FindProductOptionApiResDto fromOptions(ProductOption productOption, ProductStock productStock){
            return new FindProductOptionApiResDto(productOption.getProductOptionId(), productOption.getType(), productOption.getOptionName(),
                    productOption.getOptionValue(), productOption.getPrice(), productStock.getStock());
        }
    }

    public static FindProductApiResDto fromSingleProduct(Product product, ProductStock productStock){
        return new FindProductApiResDto(product.getProductId(), product.getName(), product.getType(), product.getPrice(),
                productStock.getStock(), null);
    }

    public static FindProductApiResDto fromOptionProduct(Product product, List<FindProductOptionApiResDto> optionList){
        int totalStock = optionList.stream().mapToInt(FindProductOptionApiResDto::stock).sum();

        return new FindProductApiResDto(product.getProductId(), product.getName(), product.getType(), product.getPrice(),
                totalStock, optionList);
    }
}
