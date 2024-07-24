package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class OrderEnums {

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        WAIT, DEPOSIT_COMPLETE, CANCEL_COMPLETE
    }

    @Getter
    @RequiredArgsConstructor
    public enum PaymentStatus {
        WAIT, PAY_COMPLETE, REFUND, FAIL
    }

    @Getter
    @RequiredArgsConstructor
    public enum Error {
        NO_ORDER_SHEET("NO_ORDER_SHEET","주문서 정보가 없습니다."),
        NO_ORDER("NO_ORDER","주문 정보가 없습니다."),

        ;
        private final String code;
        private final String message;
    }
}
