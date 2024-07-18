package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;

public interface OrderRepository {
    Order save(Order order);
    Order findByBuyerIdAndOrderId(Long buyerId, Long orderId);
}
