package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItemSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemSheetJpaRepository extends JpaRepository<OrderItemSheet, Long> {
}
