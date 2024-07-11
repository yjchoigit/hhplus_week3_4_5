package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder_orderId(Long orderId);

    @Query("SELECT oi.productId, SUM(oi.buyCnt) AS totalBuyCnt " +
            "FROM OrderItem oi " +
            "WHERE oi.createDatetime BETWEEN :startDatetime AND :endDatetime " +
            "GROUP BY oi.productId " +
            "ORDER BY totalBuyCnt DESC")
    List<Object[]> findTopProductsByBuyCnt(LocalDateTime startDatetime, LocalDateTime endDatetime);
}
