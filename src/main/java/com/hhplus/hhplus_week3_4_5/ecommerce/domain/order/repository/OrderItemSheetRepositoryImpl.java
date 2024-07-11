package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItemSheet;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemSheetRepositoryImpl implements OrderItemSheetRepository {
    private final OrderItemSheetJpaRepository orderItemSheetJpaRepository;

    public OrderItemSheetRepositoryImpl(OrderItemSheetJpaRepository orderItemSheetJpaRepository) {
        this.orderItemSheetJpaRepository = orderItemSheetJpaRepository;
    }

    @Override
    public void save(OrderItemSheet orderItemSheet) {
        orderItemSheetJpaRepository.save(orderItemSheet);
    }
}
