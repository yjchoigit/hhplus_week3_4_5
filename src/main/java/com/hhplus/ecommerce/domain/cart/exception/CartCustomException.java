package com.hhplus.ecommerce.domain.cart.exception;

import com.hhplus.ecommerce.base.exception.BaseException;
import com.hhplus.ecommerce.domain.cart.CartEnums;

public class CartCustomException extends BaseException {
    public CartCustomException(CartEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }
}
