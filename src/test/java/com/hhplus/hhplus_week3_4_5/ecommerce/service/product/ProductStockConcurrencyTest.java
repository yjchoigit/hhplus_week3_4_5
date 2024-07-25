package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;


import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductStockRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductStockConcurrencyTest {
    private static final Logger log = LoggerFactory.getLogger(ProductStockConcurrencyTest.class);

    @Autowired
    private ProductStockRepository productStockRepository;

    @Autowired
    private ProductFixture productFixture;

    @Autowired
    private ProductStockServiceImpl productStockServiceImpl;


    @Test
    @DisplayName("동시 재고 차감 테스트 성공")
    void deductProductStock_currency_success() throws InterruptedException {
        // Given
        Product product = productFixture.add_usable_product();
        ProductOption option = productFixture.add_usable_product_option(product).get(0);
        productFixture.add_product_stock(product, option, 100);

        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(product.getProductId(), option.getProductOptionId());

        CountDownLatch latch = new CountDownLatch(2);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<Void> task = () -> {
            try {
                productStockServiceImpl.deductProductStock(product.getProductId(), option.getProductOptionId(), 1);
            } catch (ObjectOptimisticLockingFailureException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                latch.countDown();
            }
            return null;
        };

        // Submit tasks
        Future<Void> future1 = executorService.submit(task);
        Future<Void> future2 = executorService.submit(task);

        latch.await();

        executorService.shutdown();

        try {
            try {
                log.info("Task start!");
                future1.get();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof ObjectOptimisticLockingFailureException) {
                    System.out.println("future1 threw ObjectOptimisticLockingFailureException");
                } else {
                    throw e;
                }
            }

            try {
                log.info("Task start!");
                future2.get();
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof ObjectOptimisticLockingFailureException) {
                    log.error("Exception occurred: ", e);
                } else {
                    throw e;
                }
            }

        } catch (Exception e) {
            log.error("Exception occurred: ", e);
        }
    }

    @Test
    @DisplayName("동시 재고 차감 테스트 (10 스레드)")
    void deductProductStock_currency_success_10() throws InterruptedException, ExecutionException {
        // Given
        Product product = productFixture.add_usable_product();
        ProductOption option = productFixture.add_usable_product_option(product).get(0);
        productFixture.add_product_stock(product, option, 100);

        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(product.getProductId(), option.getProductOptionId());

        CountDownLatch latch = new CountDownLatch(10);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Callable<Void> task = () -> {
            try {
                log.info("Task start!");
                productStockServiceImpl.deductProductStock(product.getProductId(), option.getProductOptionId(), 1);
            } catch (ObjectOptimisticLockingFailureException e) {
                log.error("Exception occurred: ", e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                log.info("CountDownLatch.countDown()");
                latch.countDown();
            }
            return null;
        };

        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(task));
        }

        latch.await();

        executorService.shutdown();

        boolean anyExceptionOccurred = false;

        for (Future<Void> future : futures) {
            try {
                future.get(); // Check if it throws an exception
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof ObjectOptimisticLockingFailureException) {
                    anyExceptionOccurred = true;
                } else {
                    throw e; // Re-throw if not the expected exception
                }
            }
        }

        assertTrue(anyExceptionOccurred, "At least one task should have thrown ObjectOptimisticLockingFailureException.");
    }
}
