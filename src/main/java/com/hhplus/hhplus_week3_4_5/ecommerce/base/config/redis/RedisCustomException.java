package com.hhplus.hhplus_week3_4_5.ecommerce.base.config.redis;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.BaseException;

public class RedisCustomException extends BaseException {
    public RedisCustomException(RedisEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }

}
