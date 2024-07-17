package com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entity.Cart;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record GetCartApiResDto(
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

    public static GetCartApiResDto from(Cart cart, Product product, ProductOption productOption){
        if(productOption == null){
            return new GetCartApiResDto(cart.getCartId(), product.getProductId(), product.getName(),
                    null, null, cart.getBuyCnt());
        }
        return new GetCartApiResDto(cart.getCartId(), product.getProductId(), product.getName(),
                productOption.getProductOptionId(), productOption.optionStr(), cart.getBuyCnt());
    }
}
