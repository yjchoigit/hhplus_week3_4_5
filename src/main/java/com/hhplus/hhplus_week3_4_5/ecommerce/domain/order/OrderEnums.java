package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class OrderEnums {

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        WAIT, DEPOSIT_COMPLETE, CANCEL_COMPLETE
    }
}
