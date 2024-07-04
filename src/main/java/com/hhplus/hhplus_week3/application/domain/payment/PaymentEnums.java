package com.hhplus.hhplus_week3.application.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PaymentEnums {
    @Getter
    @RequiredArgsConstructor
    public enum Type {
        PAYMENT, REFUND
    }
}
