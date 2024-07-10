package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.payment;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.payment.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
}
