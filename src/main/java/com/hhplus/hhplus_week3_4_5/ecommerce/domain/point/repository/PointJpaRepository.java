package com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

public interface PointJpaRepository extends JpaRepository<Point, Long> {
    // 공유락
    @Lock(LockModeType.PESSIMISTIC_READ)
    Point findByBuyerId(Long buyerId);
}
