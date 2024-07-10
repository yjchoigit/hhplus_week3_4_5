package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {
}
