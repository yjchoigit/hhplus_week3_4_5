package com.hhplus.ecommerce.domain.order.repository;

import com.hhplus.ecommerce.domain.order.entity.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPaymentJpaRepository extends JpaRepository<OrderPayment, Long> {
    OrderPayment findByOrder_OrderId(Long orderId);
}
