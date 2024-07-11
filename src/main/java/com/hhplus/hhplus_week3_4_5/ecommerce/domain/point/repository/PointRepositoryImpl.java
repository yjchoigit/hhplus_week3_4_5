package com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    public PointRepositoryImpl(PointJpaRepository pointJpaRepository) {
        this.pointJpaRepository = pointJpaRepository;
    }

    @Override
    public Point findByBuyerId(Long buyerId) {
        Point point = pointJpaRepository.findByBuyerId(buyerId);
        if(point == null) {
            throw new IllegalArgumentException("잔액 정보가 없습니다.");
        }
        return point;
    }

    @Override
    public void save(Point point) {
        pointJpaRepository.save(point);
    }
}
