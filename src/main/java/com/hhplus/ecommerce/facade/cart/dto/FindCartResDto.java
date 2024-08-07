package com.hhplus.ecommerce.facade.cart.dto;

import com.hhplus.ecommerce.domain.cart.entity.Cart;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record FindCartResDto(
    Long cartId,
    Long productId,
    String productName,
    Long productOptionId,
    String productOptionName,
    int buyCnt
) implements Serializable {

    public static FindCartResDto from(Cart cart, Product product, ProductOption productOption){
        if(productOption == null){
            return new FindCartResDto(cart.getCartId(), product.getProductId(), product.getName(),
                    null, null, cart.getBuyCnt());
        }
        return new FindCartResDto(cart.getCartId(), product.getProductId(), product.getName(),
                productOption.getProductOptionId(), productOption.optionStr(), cart.getBuyCnt());
    }
}
