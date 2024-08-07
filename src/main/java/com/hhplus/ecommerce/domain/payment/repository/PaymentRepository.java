package com.hhplus.ecommerce.domain.payment.repository;

import com.hhplus.ecommerce.domain.payment.entity.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
    Payment findByOrderId(Long orderId);
}
