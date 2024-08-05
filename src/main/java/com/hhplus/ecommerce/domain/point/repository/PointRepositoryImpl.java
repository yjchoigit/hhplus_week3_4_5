package com.hhplus.ecommerce.domain.point.repository;

import com.hhplus.ecommerce.domain.point.entity.Point;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    public PointRepositoryImpl(PointJpaRepository pointJpaRepository) {
        this.pointJpaRepository = pointJpaRepository;
    }

    @Override
    public Optional<Point> findByBuyerId(Long buyerId) {
        return pointJpaRepository.findByBuyerId(buyerId);
    }

    @Override
    public Point save(Point point) {
        return pointJpaRepository.save(point);
    }
}
