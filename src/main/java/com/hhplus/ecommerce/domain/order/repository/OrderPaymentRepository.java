package com.hhplus.ecommerce.domain.order.repository;

import com.hhplus.ecommerce.domain.order.entity.OrderPayment;

public interface OrderPaymentRepository {
    OrderPayment save(OrderPayment orderPayment);
    OrderPayment findByOrderId(Long orderId);
}
