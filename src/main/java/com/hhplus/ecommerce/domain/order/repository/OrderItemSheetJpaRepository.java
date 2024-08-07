package com.hhplus.ecommerce.domain.order.repository;

import com.hhplus.ecommerce.domain.order.entity.OrderItemSheet;
import com.hhplus.ecommerce.domain.order.entity.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemSheetJpaRepository extends JpaRepository<OrderItemSheet, Long> {
    List<OrderItemSheet> findAllByOrderSheet_OrderSheetId(Long orderSheetId);
    void deleteByOrderSheet(OrderSheet orderSheet);
}
