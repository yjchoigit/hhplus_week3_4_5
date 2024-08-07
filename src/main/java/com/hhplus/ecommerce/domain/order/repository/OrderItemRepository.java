package com.hhplus.ecommerce.domain.order.repository;

import com.hhplus.ecommerce.domain.order.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository {
    OrderItem save(OrderItem orderItem);
    List<OrderItem> findByOrderId(Long orderId);

    List<Object[]> findTopProductsByBuyCnt(LocalDateTime startDatetime, LocalDateTime endDatetime);
}
