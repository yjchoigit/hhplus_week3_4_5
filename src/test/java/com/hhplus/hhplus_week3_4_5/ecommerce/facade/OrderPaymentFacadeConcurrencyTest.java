package com.hhplus.hhplus_week3_4_5.ecommerce.facade;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.facade.order.OrderPaymentFacade;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.buyer.BuyerFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.order.OrderSheetFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderPaymentFacadeConcurrencyTest {

    @Autowired
    private OrderPaymentFacade orderPaymentFacade;

    @Autowired
    private OrderSheetFixture orderSheetFixture;

    @Autowired
    private BuyerFixture buyerFixture;

    private Buyer buyer;

    @BeforeEach
    void setUp(){
        buyer = buyerFixture.add_buyer();
    }


    @Test
    @Transactional
    void test() throws InterruptedException {
        // given
        OrderSheet orderSheet = orderSheetFixture.add_order_sheet(buyer, 10);
        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(1300)
                .buyCnt(2)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .orderSheetId(orderSheet.getOrderSheetId())
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(2)
                .totalPrice(2600)
                .orderItemList(items)
                .build();


        // CountDownLatch to coordinate threads
        CountDownLatch latch = new CountDownLatch(2);

        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Callable task to be executed by multiple threads
        Callable<Boolean> task = () -> {
            try {
                orderPaymentFacade.createOrder(reqDto);
            } catch (Exception e) {
                // Expected exception due to optimistic locking
                return false;
            } finally {
                latch.countDown();
            }
            return true;
        };

        // Submit tasks
        Future<Boolean> future1 = executorService.submit(task);
        Future<Boolean> future2 = executorService.submit(task);

        // Wait for both threads to finish
        latch.await();

        // Shutdown executor service
        executorService.shutdown();

        // Verify that one of the tasks failed due to optimistic lock exception
        assertThrows(JpaOptimisticLockingFailureException.class, () -> {
            future1.get();
            future2.get();
        });

    }
}
