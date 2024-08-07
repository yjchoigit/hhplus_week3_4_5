package com.hhplus.ecommerce.service.payment;

import com.hhplus.ecommerce.domain.payment.entity.Payment;

public interface PaymentService {
    // 결제 처리
    Payment pay(Long buyerId, Long orderId);
}
