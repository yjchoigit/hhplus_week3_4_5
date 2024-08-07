package com.hhplus.ecommerce.service.point;

import com.hhplus.ecommerce.domain.point.entity.Point;
import com.hhplus.ecommerce.domain.point.entity.PointHistory;
import com.hhplus.ecommerce.domain.point.exception.PointCustomException;
import com.hhplus.ecommerce.domain.point.repository.PointHistoryRepository;
import com.hhplus.ecommerce.domain.point.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @InjectMocks
    private PointServiceImpl pointServiceImpl;

    @Mock
    private PointRepository pointRepository;

    @Mock
    private PointHistoryRepository pointHistoryRepository;

    private Point point;

    @BeforeEach
    void setUp() {
        // 잔액 등록
        point = new Point(1L, 1L, 1000);
    }

    @Test
    @DisplayName("잔액 조회 성공")
    void findPoint_success() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(Optional.ofNullable(point));

        // then
        Point result = pointServiceImpl.findPoint(buyerId);
        assertEquals(result.getAllPoint(), 1000);
    }

    @Test
    @DisplayName("잔액 조회 실패 - 회원 정보가 없을 때")
    void findPoint_no_buyer_fail() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(Optional.empty());

        // then
        assertThrows(PointCustomException.class, ()-> {
            pointServiceImpl.findPoint(buyerId);
        });
    }

    @Test
    @DisplayName("잔액 충전 성공")
    void chargePoint_success() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(Optional.ofNullable(point));

        // then
        Point result = pointServiceImpl.chargePoint(buyerId, 100);

        assertNotNull(result);
        verify(pointHistoryRepository).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("잔액 충전 실패 - 회원 정보가 없을 때")
    void chargePoint_no_buyer_fail() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(Optional.empty());

        // then
        assertThrows(PointCustomException.class, ()-> {
            pointServiceImpl.chargePoint(buyerId, 100);
        });
    }

    @Test
    @DisplayName("잔액 사용 성공")
    void usePoint_success() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(Optional.ofNullable(point));

        // then
        Point result = pointServiceImpl.usePoint(buyerId, 100);

        assertNotNull(result);
        verify(pointHistoryRepository).save(any(PointHistory.class));
    }
}