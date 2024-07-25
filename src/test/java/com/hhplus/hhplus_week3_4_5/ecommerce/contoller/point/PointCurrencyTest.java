package com.hhplus.hhplus_week3_4_5.ecommerce.contoller.point;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.point.PointFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.point.PointServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PointCurrencyTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PointFixture pointFixture;

    @Autowired
    private BuyerFixture buyerFixture;

    @Autowired
    private PointServiceImpl pointService;

    @Test
    @Transactional
    public void testPessimisticReadLockInChargePoint() throws InterruptedException {
        // Given
        Buyer buyer = buyerFixture.add_buyer();
        pointFixture.add_point(buyer.getBuyerId(), 1000);

        // Latch to control the execution order of threads
        CountDownLatch latch = new CountDownLatch(1);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            try {
                // Transaction 1: Charge point
                pointService.chargePoint(buyer.getBuyerId(), 50);
                latch.countDown(); // Allow Transaction 2 to proceed
                TimeUnit.SECONDS.sleep(5); // Hold the transaction for 5 seconds
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                latch.await(); // Wait for Transaction 1 to start and lock the row
                // Transaction 2: Try to charge point
                pointService.chargePoint(buyer.getBuyerId(), 50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

//        Point updatedPoint = pointRepository.findByBuyerId(buyerId);
//        assertThat(updatedPoint.getAmount()).isEqualTo(200); // Assuming initial amount was 100 and both charges succeeded
    }
}
