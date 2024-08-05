package com.hhplus.ecommerce.domain.point.exception;

import com.hhplus.ecommerce.base.exception.BaseException;
import com.hhplus.ecommerce.domain.point.PointEnums;

public class PointCustomException extends BaseException {
    public PointCustomException(PointEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }
}
