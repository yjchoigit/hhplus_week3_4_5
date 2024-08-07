package com.hhplus.ecommerce.domain.point.repository;

import com.hhplus.ecommerce.domain.point.entity.Point;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface PointJpaRepository extends JpaRepository<Point, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Point> findByBuyerId(Long buyerId);
}
