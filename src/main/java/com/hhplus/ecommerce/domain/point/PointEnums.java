package com.hhplus.ecommerce.domain.point;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PointEnums {
    @Getter
    @RequiredArgsConstructor
    public enum Type {
        DEDUCT, CHARGE
    }

    @Getter
    @RequiredArgsConstructor
    public enum Error {
        OUT_OF_POINT("OUT_OF_POINT","잔액이 부족합니다."),
        NO_POINT("NO_POINT","잔액 정보가 없습니다."),
        ;
        private final String code;
        private final String message;
    }
}
