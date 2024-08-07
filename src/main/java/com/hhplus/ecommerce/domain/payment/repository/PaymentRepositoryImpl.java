package com.hhplus.ecommerce.domain.payment.repository;

import com.hhplus.ecommerce.domain.payment.entity.Payment;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private PaymentJpaRepository paymentJpaRepository;

    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
    }

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

    @Override
    public Payment findByOrderId(Long orderId) {
        return paymentJpaRepository.findByOrder_OrderId(orderId);
    }
}
