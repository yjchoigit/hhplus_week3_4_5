package com.hhplus.ecommerce.domain.point.repository;

import com.hhplus.ecommerce.domain.point.entity.PointHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    private final PointHistoryJpaRepository pointHistoryJpaRepository;

    public PointHistoryRepositoryImpl(PointHistoryJpaRepository pointHistoryJpaRepository) {
        this.pointHistoryJpaRepository = pointHistoryJpaRepository;
    }

    @Override
    public void save(PointHistory pointHistory) {
        pointHistoryJpaRepository.save(pointHistory);
    }

    @Override
    public List<PointHistory> findByPointId(Long pointId) {
        return pointHistoryJpaRepository.findAllByPoint_PointId(pointId);
    }
}
