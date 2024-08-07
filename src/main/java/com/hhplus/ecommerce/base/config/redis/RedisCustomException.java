package com.hhplus.ecommerce.base.config.redis;

import com.hhplus.ecommerce.base.exception.BaseException;

public class RedisCustomException extends BaseException {
    public RedisCustomException(RedisEnums.Error error) {
        super(error.getCode(), error.getMessage());
    }

}
