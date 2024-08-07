package com.hhplus.ecommerce.domain.order.repository;

import com.hhplus.ecommerce.domain.order.entity.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSheetJpaRepository extends JpaRepository<OrderSheet, Long> {
    OrderSheet findFirstByBuyerId(Long buyerId);
}
