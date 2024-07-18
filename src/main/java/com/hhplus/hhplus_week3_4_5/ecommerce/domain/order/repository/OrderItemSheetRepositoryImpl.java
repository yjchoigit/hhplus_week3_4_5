package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItemSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemSheetRepositoryImpl implements OrderItemSheetRepository {
    private final OrderItemSheetJpaRepository orderItemSheetJpaRepository;

    public OrderItemSheetRepositoryImpl(OrderItemSheetJpaRepository orderItemSheetJpaRepository) {
        this.orderItemSheetJpaRepository = orderItemSheetJpaRepository;
    }

    @Override
    public OrderItemSheet save(OrderItemSheet orderItemSheet) {
        return orderItemSheetJpaRepository.save(orderItemSheet);
    }

    @Override
    public List<OrderItemSheet> findByOrderSheetId(Long orderSheetId) {
        return orderItemSheetJpaRepository.findAllByOrderSheet_OrderSheetId(orderSheetId);
    }

    @Override
    public void deleteByOrderSheet(OrderSheet orderSheet) {
        orderItemSheetJpaRepository.deleteByOrderSheet(orderSheet);
    }
}
