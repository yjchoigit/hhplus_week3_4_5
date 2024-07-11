package com.hhplus.hhplus_week3_4_5.ecommerce.service.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.point.dto.FindPointHistoryApiResDto;

import java.util.List;

public interface PointService {

    // 잔액 조회
    int findPoint(Long buyerId);

    // 잔액 충전
    boolean chargePoint(Long buyerId, int point);

    boolean usePoint(Long buyerId, int point);
    List<FindPointHistoryApiResDto> findPointHistoryList(Long buyerId);
}
