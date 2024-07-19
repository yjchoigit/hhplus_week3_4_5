package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItemSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;

import java.util.List;

public interface OrderItemSheetRepository {
    OrderItemSheet save(OrderItemSheet orderItemSheet);
    List<OrderItemSheet> findByOrderSheetId(Long orderSheetId);
    void deleteByOrderSheet(OrderSheet orderSheet);
}
