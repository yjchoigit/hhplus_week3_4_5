package com.hhplus.hhplus_week3_4_5.ecommerce.controller.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class BaseEnums {
    @Getter
    @RequiredArgsConstructor
    public enum ResponseStatus {
        SUCCESS, FAILURE, ERROR
    }
}
