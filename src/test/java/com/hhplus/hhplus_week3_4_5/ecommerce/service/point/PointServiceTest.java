package com.hhplus.hhplus_week3_4_5.ecommerce.service.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.PointHistory;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository.PointHistoryRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository.PointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(point);

        // then
        int result = pointServiceImpl.findPoint(buyerId);
        assertEquals(result, 1000);
    }

    @Test
    @DisplayName("잔액 조회 실패 - 회원 정보가 없을 때")
    void findPoint_no_buyer_fail() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(null);

        // then
        assertThrows(NullPointerException.class, ()-> {
            pointServiceImpl.findPoint(buyerId);
        });
    }

    @Test
    @DisplayName("잔액 충전 성공")
    void chargePoint_success() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(point);

        // then
        boolean result = pointServiceImpl.chargePoint(buyerId, 100);

        assertTrue(result);
        verify(pointHistoryRepository).save(any(PointHistory.class));
    }

    @Test
    @DisplayName("잔액 충전 실패 - 회원 정보가 없을 때")
    void chargePoint_no_buyer_fail() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(null);

        // then
        assertThrows(NullPointerException.class, ()-> {
            pointServiceImpl.chargePoint(buyerId, 100);
        });
    }

    @Test
    @DisplayName("잔액 사용 성공")
    void usePoint_success() {
        // given
        Long buyerId = 1L;

        // when
        when(pointRepository.findByBuyerId(buyerId)).thenReturn(point);

        // then
        boolean result = pointServiceImpl.usePoint(buyerId, 100);

        assertTrue(result);
        verify(pointHistoryRepository).save(any(PointHistory.class));
    }
}