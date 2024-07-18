package com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.exception;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.BaseException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.PointEnums;

public class PointCustomException extends BaseException {
    public PointCustomException(PointEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }
}
