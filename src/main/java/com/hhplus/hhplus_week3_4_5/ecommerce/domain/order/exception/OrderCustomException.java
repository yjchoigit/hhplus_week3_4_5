package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.exception;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.BaseException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;

public class OrderCustomException extends BaseException {
    public OrderCustomException(OrderEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }
}
