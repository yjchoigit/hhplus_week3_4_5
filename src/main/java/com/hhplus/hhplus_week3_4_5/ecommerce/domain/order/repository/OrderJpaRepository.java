package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Orders, Long> {
}
