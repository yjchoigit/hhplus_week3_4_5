package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPaymentJpaRepository extends JpaRepository<OrderPayment, Long> {
    OrderPayment findByOrder_OrderId(Long orderId);
}
