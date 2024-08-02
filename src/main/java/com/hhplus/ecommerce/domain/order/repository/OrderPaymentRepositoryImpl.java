package com.hhplus.ecommerce.domain.order.repository;

import com.hhplus.ecommerce.domain.order.entity.OrderPayment;
import org.springframework.stereotype.Repository;

@Repository
public class OrderPaymentRepositoryImpl implements OrderPaymentRepository {
    private OrderPaymentJpaRepository orderPaymentJpaRepository;

    public OrderPaymentRepositoryImpl(OrderPaymentJpaRepository orderPaymentJpaRepository) {
        this.orderPaymentJpaRepository = orderPaymentJpaRepository;
    }

    @Override
    public OrderPayment save(OrderPayment orderPayment) {
        return orderPaymentJpaRepository.save(orderPayment);
    }

    @Override
    public OrderPayment findByOrderId(Long orderId) {
        return orderPaymentJpaRepository.findByOrder_OrderId(orderId);
    }
}
