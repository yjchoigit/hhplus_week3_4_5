package com.hhplus.ecommerce.service.point;

import com.hhplus.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.ecommerce.domain.point.entity.Point;
import com.hhplus.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.ecommerce.fixture.point.PointFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class PointConcurrencyTest {
    private static final Logger log = LoggerFactory.getLogger(PointConcurrencyTest.class);

    @Autowired
    private PointServiceImpl pointServiceImpl;

    @Autowired
    private PointFixture pointFixture;

    @Autowired
    private BuyerFixture buyerFixture;

    @Test
    @DisplayName("잔액 사용 동시성 테스트 성공 - 스레드 (10)")
    void usePoint_currency_success() throws InterruptedException {
        // given
        // 잔액 100 세팅
        Buyer buyer = buyerFixture.add_buyer();
        Point point = pointFixture.add_point(buyer.getBuyerId(), 100);

        // CountDownLatch
        CountDownLatch latch = new CountDownLatch(10);

        // 스레드 10개 설정
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        AtomicInteger successfulLockCount = new AtomicInteger(0);

        // Callable task
        Callable<Void> task = () -> {
            try {
                log.info("Task start!");
                // 잔액 사용 10원씩 실행
                Point usePoint = pointServiceImpl.usePoint(buyer.getBuyerId(), 10);
                if (usePoint != null) {
                    successfulLockCount.incrementAndGet(); // 락 획득 성공 시 카운트 증가
                }
            } catch (Exception e) {
                log.error("Exception occurred: ", e);
            } finally {
                log.info("CountDownLatch.countDown()");
                latch.countDown();
            }
            return null;
        };

        // 10개 task 전송
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(task));
        }

        // task 기다리기
        latch.await();

        executorService.shutdown();

        // 잔액 확인
        Point finalPoint = pointServiceImpl.findPoint(buyer.getBuyerId());
        int finalSuccessfulLockCount = successfulLockCount.get();
        int expectedBalance = point.getAllPoint() - (10 * finalSuccessfulLockCount);
        assertEquals(expectedBalance, finalPoint.getAllPoint(), "The point should be reduced correctly after all deductions.");
    }

    @Test
    @DisplayName("잔액 충전 동시성 테스트 성공 - 스레드 (10)")
    void chargePoint_currency_success() throws InterruptedException {
        // given
        // 잔액 100 세팅
        Buyer buyer = buyerFixture.add_buyer();
        Point point = pointFixture.add_point(buyer.getBuyerId(), 0);

        // CountDownLatch
        CountDownLatch latch = new CountDownLatch(10);

        // 스레드 10개 설정
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        AtomicInteger successfulLockCount = new AtomicInteger(0);

        // Callable task
        Callable<Void> task = () -> {
            try {
                log.info("Task start!");
                // 잔액 사용 10원씩 실행
                Point chargePoint = pointServiceImpl.chargePoint(buyer.getBuyerId(), 10);
                if (chargePoint != null) {
                    successfulLockCount.incrementAndGet(); // 락 획득 성공 시 카운트 증가
                }
            } catch (Exception e) {
                log.error("Exception occurred: ", e);
            } finally {
                log.info("CountDownLatch.countDown()");
                latch.countDown();
            }
            return null;
        };

        // 10개 task 전송
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(task));
        }

        // task 기다리기
        latch.await();

        executorService.shutdown();

        // 잔액 확인
        Point finalPoint = pointServiceImpl.findPoint(buyer.getBuyerId());
        int finalSuccessfulLockCount = successfulLockCount.get();
        int expectedBalance = point.getAllPoint() + (10 * finalSuccessfulLockCount);
        assertEquals(expectedBalance, finalPoint.getAllPoint(), "The point should be reduced correctly after all deductions.");
    }
}
