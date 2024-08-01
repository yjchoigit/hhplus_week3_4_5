package com.hhplus.hhplus_week3_4_5.ecommerce.base.config.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

public class RedisEnums {
    @Getter
    @RequiredArgsConstructor
    public enum LockName {
        CREATE_ORDER("CREATE_ORDER_{0}", "주문 생성 시 orderSheetId 기준으로 lock 처리"),
        PAYMENT_ORDER("PAYMENT_ORDER_{0}", "결제 진행 시 orderId 기준으로 lock 처리"),
        CHARGE_POINT("CHARGE_POINT_{0}", "잔액 충전 시 buyerId 기준으로 lock 처리"),
        USE_POINT("USE_POINT_{0}", "잔액 사용 시 buyerId 기준으로 lock 처리"),
        DEDUCT_PRODUCT("DEDUCT_PRODUCT_{0}_{1}", "상품 재고 차감 시 productId, productOptionId 기준으로 lock 처리"),
        ;

        public String changeLockName(Long id) {
            return MessageFormat.format(this.lock, id);
        }
        public String changeLockName(Long id1, Long id2) {
            return MessageFormat.format(this.lock, id1, id2);
        }

        private final String lock;
        private final String description;

    }

    @Getter
    @RequiredArgsConstructor
    public enum Error {
        LOCK_NOT_ACQUIRE("LOCK_NOT_ACQUIRE","락을 얻는데 실패했습니다."),
        LOCK_INTERRUPTED_ERROR("LOCK_INTERRUPTED_ERROR","락을 얻으려고 시도하다가 인터럽트가 발생했습니다."),
        UNLOCKING_A_LOCK_WHICH_IS_NOT_LOCKED("UNLOCKING_A_LOCK_WHICH_IS_NOT_LOCKED", "이미 종료된 락입니다.")
        ;
        private final String code;
        private final String message;
    }
}
