package com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.exception;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.BaseException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.CartEnums;

public class CartCustomException extends BaseException {
    public CartCustomException(CartEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }
}
