package com.hhplus.ecommerce.domain.point.event;

import com.hhplus.ecommerce.domain.order.event.dto.OrderPaymentCompleteEvent;
import com.hhplus.ecommerce.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointEventListener {
    private final PointService pointService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void usePoint(OrderPaymentCompleteEvent event){
        log.info("잔액 사용처리 실행");
        pointService.usePoint(event.buyerId(), event.payment().getPaymentPrice());
    }
}
