package com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PointJpaRepository extends JpaRepository<Point, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Point findByBuyerId(Long buyerId);
}
