package com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.PointHistory;

import java.util.List;

public interface PointHistoryRepository {
    void save(PointHistory pointHistory);

    List<PointHistory> findByPointId(Long pointId);
}
