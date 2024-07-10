package com.hhplus.hhplus_week3_4_5.ecommerce.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PaymentEnums {
    @Getter
    @RequiredArgsConstructor
    public enum Type {
        PAYMENT, REFUND
    }
}
