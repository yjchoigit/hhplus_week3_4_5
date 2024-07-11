package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final OrderItemJpaRepository orderItemJpaRepository;

    public OrderItemRepositoryImpl(OrderItemJpaRepository orderItemJpaRepository) {
        this.orderItemJpaRepository = orderItemJpaRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemJpaRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemJpaRepository.findAllByOrder_orderId(orderId);
    }

    @Override
    public List<Object[]> findTopProductsByBuyCnt(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        return orderItemJpaRepository.findTopProductsByBuyCnt(startDatetime, endDatetime);
    }
}
