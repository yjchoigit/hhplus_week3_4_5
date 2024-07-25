package com.hhplus.hhplus_week3_4_5.ecommerce.contoller.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.point.PointFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.point.PointServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class PointCurrencyTest {

    @Autowired
    private PointFixture pointFixture;

    @Autowired
    private BuyerFixture buyerFixture;

    @Autowired
    private PointServiceImpl pointService;

    @Test
    @DisplayName("공유락 적용 - 잔액 충전 N번")
    void testPessimisticReadLockInChargePoint() throws InterruptedException {
        // Given
        Buyer buyer = buyerFixture.add_buyer();
        pointFixture.add_point(buyer.getBuyerId(), 100);

        // Latch to control the execution order of threads
        CountDownLatch latch = new CountDownLatch(1);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try {
                // Transaction 1: Charge point
                log.info("Thread 1 starting");
                pointService.chargePoint(buyer.getBuyerId(), 50);
                latch.countDown(); // Allow Transaction 2 to proceed
                TimeUnit.SECONDS.sleep(5); // Hold the transaction for 5 seconds
                log.info("Thread 1 finished");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                latch.await(); // Wait for Transaction 1 to start and lock the row
                log.info("Thread 2 starting");
                // Transaction 2: Try to charge point
                pointService.chargePoint(buyer.getBuyerId(), 50);
                log.info("Thread 2 finished");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        int allPoint = pointService.findPoint(buyer.getBuyerId());
        assertThat(allPoint).isEqualTo(200);
    }

}
