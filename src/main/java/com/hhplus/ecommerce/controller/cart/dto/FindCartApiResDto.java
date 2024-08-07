package com.hhplus.ecommerce.controller.cart.dto;

import com.hhplus.ecommerce.domain.cart.entity.Cart;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.ecommerce.facade.cart.dto.FindCartResDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record FindCartApiResDto(
    @Schema(description = "장바구니 ID")
    Long cartId,
    @Schema(description = "상품 ID")
    Long productId,
    @Schema(description = "상품명")
    String productName,
    @Schema(description = "상품 옵션 ID")
    Long productOptionId,
    @Schema(description = "상품옵션명")
    String productOptionName,
    @Schema(description = "구매 수량")
    int buyCnt
) implements Serializable {

    public static FindCartApiResDto from(FindCartResDto resDto){
        return new FindCartApiResDto(resDto.cartId(), resDto.productId(), resDto.productName(),
                resDto.productOptionId(), resDto.productOptionName(), resDto.buyCnt());
    }
}
