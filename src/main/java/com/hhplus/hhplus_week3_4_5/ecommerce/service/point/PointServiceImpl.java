package com.hhplus.hhplus_week3_4_5.ecommerce.service.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.point.dto.GetPointHistoryApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.PointEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.PointHistory;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository.PointHistoryRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class PointServiceImpl implements PointService {
    private PointRepository pointRepository;
    private PointHistoryRepository pointHistoryRepository;

    // 잔액 조회
    @Override
    public int findPoint(Long buyerId){
        // 회원 id로 잔액 정보 조회
        Point pointInfo = pointRepository.findByBuyerId(buyerId);
        // 총 포인트 반환
        return pointInfo.getAllPoint();
    }

    // 잔액 충전
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean chargePoint(Long buyerId, int point){
        // 회원 id로 잔액 정보 조회
        Point pointInfo = pointRepository.findByBuyerId(buyerId);
        // 잔액 충전
        pointInfo.charge(point);
        // 잔액 충전 내역 저장
        pointHistoryRepository.save(new PointHistory(pointInfo, PointEnums.Type.CHARGE, point));
        return true;
    }

    // 잔액 사용
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean usePoint(Long buyerId, int point) {
        // 회원 id로 잔액 정보 조회
        Point pointInfo = pointRepository.findByBuyerId(buyerId);
        // 잔액 사용
        pointInfo.use(point);
        // 잔액 사용 내역 저장
        pointHistoryRepository.save(new PointHistory(pointInfo, PointEnums.Type.DEDUCT, point));
        return false;
    }

    @Override
    public List<GetPointHistoryApiResDto> findPointHistoryList(Long buyerId) {
        // 회원 id로 잔액 정보 조회
        Point pointInfo = pointRepository.findByBuyerId(buyerId);
        // 잔액 id로 내역 조회
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByPointId(1L);
        if(pointHistoryList.isEmpty()){
            return new ArrayList<>();
        }
        return pointHistoryList.stream().map(GetPointHistoryApiResDto::from).toList();
    }
}
