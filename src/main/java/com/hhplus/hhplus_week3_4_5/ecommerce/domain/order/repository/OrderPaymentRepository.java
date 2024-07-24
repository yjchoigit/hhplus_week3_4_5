package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderPayment;

public interface OrderPaymentRepository {
    OrderPayment save(OrderPayment orderPayment);
    OrderPayment findByOrderId(Long orderId);
}
