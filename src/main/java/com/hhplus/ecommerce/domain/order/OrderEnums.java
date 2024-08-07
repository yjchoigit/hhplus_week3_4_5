package com.hhplus.ecommerce.domain.order;

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
        WAIT, PAY_COMPLETE, REFUND
    }

    @Getter
    @RequiredArgsConstructor
    public enum Error {
        NO_ORDER_SHEET("NO_ORDER_SHEET","주문서 정보가 없습니다."),
        NO_ORDER("NO_ORDER","주문 정보가 없습니다."),
        ALREADY_PAY_COMPLETE_ORDER("ALREADY_PAY_COMPLETE_ORDER", "이미 결제가 완료된 주문입니다."),
        ALREADY_REFUND_ORDER("ALREADY_REFUND_ORDER", "이미 환불된 주문입니다.")
        ;
        private final String code;
        private final String message;
    }
}
