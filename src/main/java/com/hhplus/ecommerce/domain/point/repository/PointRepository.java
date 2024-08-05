package com.hhplus.ecommerce.domain.point.repository;

import com.hhplus.ecommerce.domain.point.entity.Point;

import java.util.Optional;

public interface PointRepository {
    Optional<Point> findByBuyerId(Long buyerId);
    Point save(Point point);
}
