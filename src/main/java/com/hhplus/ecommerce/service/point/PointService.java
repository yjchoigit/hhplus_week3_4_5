package com.hhplus.ecommerce.service.point;

import com.hhplus.ecommerce.controller.point.dto.FindPointHistoryApiResDto;

import java.util.List;

public interface PointService {

    // 잔액 조회
    int findPoint(Long buyerId);

    // 잔액 충전
    boolean chargePoint(Long buyerId, int point);

    boolean usePoint(Long buyerId, int point);
    List<FindPointHistoryApiResDto> findPointHistoryList(Long buyerId);
}
