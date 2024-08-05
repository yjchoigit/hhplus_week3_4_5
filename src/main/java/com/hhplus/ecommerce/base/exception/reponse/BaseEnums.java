package com.hhplus.ecommerce.base.exception.reponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class BaseEnums {
    @Getter
    @RequiredArgsConstructor
    public enum ResponseStatus {
        SUCCESS("SUCCESS", "성공"),
        FAILURE("FAILURE", "실패"),
        ERROR("ERROR", "에러 발생"),
        EXCEPTION_VALIDATION("EXCEPTION_VALIDATION", "요청 값 valid 실패"),
        ;

        private final String code;
        private final String message;
    }
}
