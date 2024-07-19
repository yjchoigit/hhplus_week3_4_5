package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.exception;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.BaseException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;

public class ProductCustomException extends BaseException {
    public ProductCustomException(ProductEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }
}
