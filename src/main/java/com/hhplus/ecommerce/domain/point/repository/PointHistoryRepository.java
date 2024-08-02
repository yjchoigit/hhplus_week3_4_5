package com.hhplus.ecommerce.domain.point.repository;

import com.hhplus.ecommerce.domain.point.entity.PointHistory;

import java.util.List;

public interface PointHistoryRepository {
    void save(PointHistory pointHistory);

    List<PointHistory> findByPointId(Long pointId);
}
