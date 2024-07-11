package com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;

public interface PointRepository {
    Point findByBuyerId(Long buyerId);
    void save(Point point);
}
