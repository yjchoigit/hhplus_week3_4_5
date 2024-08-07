package com.hhplus.ecommerce.domain.payment.repository;

import com.hhplus.ecommerce.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
    Payment findByOrder_OrderId(Long orderId);
}
