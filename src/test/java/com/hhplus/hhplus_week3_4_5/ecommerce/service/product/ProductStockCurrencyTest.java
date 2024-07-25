package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductStockCurrencyTest {
    private static final Logger log = LoggerFactory.getLogger(ProductStockCurrencyTest.class);

    @Autowired
    private ProductFixture productFixture;

    @Autowired
    private ProductStockServiceImpl productStockServiceImpl;

    @Test
    @DisplayName("재고 차감 동시성 테스트 성공 - 스레드 (10)")
    void deductProductStock_currency_success() throws InterruptedException {
        // given
        // 상품 재고 100개 세팅
        Product product = productFixture.add_usable_product();
        ProductOption option = productFixture.add_usable_product_option(product).get(0);
        productFixture.add_product_stock(product, option, 100);

        // CountDownLatch
        CountDownLatch latch = new CountDownLatch(10);

        // 스레드 10개 설정
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Callable task
        Callable<Void> task = () -> {
            try {
                // 재고 차감 10개씩 실행`
                log.info("task start !");
                productStockServiceImpl.deductProductStock(product.getProductId(), option.getProductOptionId(), 10);
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

        // 재고가 10개씩 차감되었는지 확인 100 - (10 * 10) = 0
        ProductStock finalProductStock = productStockServiceImpl.findProductStockByProductIdAndProductOptionId(product.getProductId(), option.getProductOptionId());
        assertEquals(0, finalProductStock.getStock(), "The product stock should be reduced to 0 after all deductions.");
    }

    @Test
    @DisplayName("재고 차감 동시성 테스트 실패 - 스레드 (10)")
    void deductProductStock_currency_fail() throws InterruptedException {
        // given
        // 상품 재고 100개 세팅
        Product product = productFixture.add_usable_product();
        ProductOption option = productFixture.add_usable_product_option(product).get(0);
        productFixture.add_product_stock(product, option, 100);

        // CountDownLatch
        CountDownLatch latch = new CountDownLatch(10);

        // 스레드 10개 설정
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Callable task
        Callable<Void> task = () -> {
            try {
                // 재고 차감 10개씩 실행`
                log.info("task start !");
                productStockServiceImpl.deductProductStock(product.getProductId(), option.getProductOptionId(), 10);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
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

        // 재고가 10개씩 차감되었는지 확인 100 - (10 * 10) = 0
        ProductStock finalProductStock = productStockServiceImpl.findProductStockByProductIdAndProductOptionId(product.getProductId(), option.getProductOptionId());
        assertEquals(0, finalProductStock.getStock(), "The product stock should be reduced to 0 after all deductions.");
    }
}
