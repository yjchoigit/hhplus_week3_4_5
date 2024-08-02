package com.hhplus.ecommerce.domain.order.exception;

import com.hhplus.ecommerce.base.exception.BaseException;
import com.hhplus.ecommerce.domain.order.OrderEnums;

public class OrderCustomException extends BaseException {
    public OrderCustomException(OrderEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }
}
