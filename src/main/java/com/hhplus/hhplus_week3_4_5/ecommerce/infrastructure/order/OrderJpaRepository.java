package com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Orders, Long> {
}
