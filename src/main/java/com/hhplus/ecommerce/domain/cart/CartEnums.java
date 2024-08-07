package com.hhplus.ecommerce.domain.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CartEnums {
    @Getter
    @RequiredArgsConstructor
    public enum Error {
        NO_CART("NO_CART","장바구니 정보가 없습니다."),
        ;
        private final String code;
        private final String message;
    }
}
