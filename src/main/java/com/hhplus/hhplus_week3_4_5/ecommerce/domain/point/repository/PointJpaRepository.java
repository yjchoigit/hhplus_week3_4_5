package com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJpaRepository extends JpaRepository<Point, Long> {
}
