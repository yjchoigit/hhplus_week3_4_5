package com.hhplus.hhplus_week3_4_5.ecommerce.service.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.entity.Point;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.point.PointFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductStockCurrencyTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PointCurrencyTest {
    private static final Logger log = LoggerFactory.getLogger(ProductStockCurrencyTest.class);

    @Autowired
    private PointServiceImpl pointServiceImpl;

    @Autowired
    private PointFixture pointFixture;

    @Autowired
    private BuyerFixture buyerFixture;

    @Test
    @DisplayName("잔액 사용 동시서 테스트 성공 - 스레드 (10)")
    void usePoint_currency_success() throws InterruptedException {
        // given
        // 잔액 100 세팅
        Buyer buyer = buyerFixture.add_buyer();
        Point point = pointFixture.add_point(buyer.getBuyerId(), 100);

        // CountDownLatch
        CountDownLatch latch = new CountDownLatch(10);

        // 스레드 10개 설정
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Callable task
        Callable<Void> task = () -> {
            try {
                log.info("Task start!");
                // 잔액 사용 10원씩 실행
                pointServiceImpl.usePoint(buyer.getBuyerId(), 10);
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

        // 잔액이 10원씩 차감되었는지 확인 100 - (10 * 10) = 0
        int finalPoint = pointServiceImpl.findPoint(buyer.getBuyerId());
        int expectedBalance = point.getAllPoint() - 10 * 10;
        assertEquals(expectedBalance, finalPoint, "The point should be reduced correctly after all deductions.");
    }

}
