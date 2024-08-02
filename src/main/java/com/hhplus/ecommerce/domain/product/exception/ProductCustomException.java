package com.hhplus.ecommerce.domain.product.exception;

import com.hhplus.ecommerce.base.exception.BaseException;
import com.hhplus.ecommerce.domain.product.ProductEnums;

public class ProductCustomException extends BaseException {
    public ProductCustomException(ProductEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }
}
