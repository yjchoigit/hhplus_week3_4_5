package com.hhplus.hhplus_week3_4_5.ecommerce.contoller.product;


import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductStockRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product.ProductFixture;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductStockServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductStockConcurrencyTest {
    @Autowired
    private ProductStockRepository productStockRepository;

    @Autowired
    private ProductFixture productFixture;

    @Autowired
    private ProductStockServiceImpl productStockServiceImpl; // assuming you have a service layer

    @Test
    @DisplayName("동시 재고 차감 테스트")
//    @Transactional
    void testConcurrentStockDeduction() throws InterruptedException, ExecutionException {
        // Given
        Product product = productFixture.add_usable_product();
        ProductOption option = productFixture.add_usable_product_option(product).get(0);
        productFixture.add_product_stock(product, option, 100);

        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(product.getProductId(), option.getProductOptionId());

        // CountDownLatch to coordinate threads
        CountDownLatch latch = new CountDownLatch(2);

        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Callable task to be executed by multiple threads
        Callable<Void> task = () -> {
            try {
                productStockServiceImpl.deductProductStock(product.getProductId(), option.getProductOptionId(), 1);
            } catch (JpaOptimisticLockingFailureException e) {
                // Expected exception due to optimistic locking
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

        // Wait for both threads to finish
        latch.await();

        // Shutdown executor service
        executorService.shutdown();

        // Check the result of both futures
        try {
            future1.get();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof JpaOptimisticLockingFailureException) {
                // Expected exception was caught
            } else {
                throw e;
            }
        }

        try {
            future2.get();
        } catch (ExecutionException e) {
            if (e.getCause() instanceof JpaOptimisticLockingFailureException) {
                // Expected exception was caught
            } else {
                throw e;
            }
        }
    }


    @Test
    @DisplayName("동시 재고 차감 테스트 ---- ")
//    @Transactional
    void testConcurrentStockDeduction1() throws InterruptedException {
        // Given
        Product product = productFixture.add_usable_product();
        ProductOption option = productFixture.add_usable_product_option(product).get(0);
        productFixture.add_product_stock(product, option, 100);

        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(product.getProductId(), option.getProductOptionId());

        // CountDownLatch to coordinate threads
        CountDownLatch latch = new CountDownLatch(2);

        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Callable task to be executed by multiple threads
        Callable<Void> task = () -> {
            try {
                productStockServiceImpl.deductProductStock(product.getProductId(), option.getProductOptionId(), 1);
            } catch (ObjectOptimisticLockingFailureException e) {
                // Expected exception due to optimistic locking
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

        // Wait for both threads to finish
        latch.await();

        // Shutdown executor service
        executorService.shutdown();

        try {
            // Handle future1 result
            try {
                System.out.println("future1.get() start");
                future1.get(); // This could throw ExecutionException if the task threw an exception
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof ObjectOptimisticLockingFailureException) {
                    // Handle the expected exception
                    System.out.println("future1 threw ObjectOptimisticLockingFailureException");
                } else {
                    throw e; // Re-throw if not the expected exception
                }
            }

            // Handle future2 result
            try {
                System.out.println("future2.get() start");
                future2.get(); // This could also throw ExecutionException
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof ObjectOptimisticLockingFailureException) {
                    // Handle the expected exception
                    System.out.println("future2 threw ObjectOptimisticLockingFailureException");
                } else {
                    throw e; // Re-throw if not the expected exception
                }
            }

        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e.getMessage());
        }

//
//        /// Verify that one task threw ObjectOptimisticLockingFailureException
//        Throwable cause1 = assertThrows(ExecutionException.class, future1::get).getCause();
//        Throwable cause2 = assertThrows(ExecutionException.class, future2::get).getCause();
//
//        assertTrue(cause1 instanceof ObjectOptimisticLockingFailureException || cause2 instanceof ObjectOptimisticLockingFailureException,
//                "One of the tasks should have thrown ObjectOptimisticLockingFailureException");
//
//        // Verify that the remaining task completed successfully
//        assertTrue(cause1 == null || cause2 == null,
//                "At least one task should have completed successfully.");

//        // Verify that at least one task failed with an ObjectOptimisticLockingFailureException
//        ExecutionException executionException1 = assertThrows(ExecutionException.class, future1::get);
//        ExecutionException executionException2 = assertThrows(ExecutionException.class, future2::get);
//
//        Throwable cause1 = executionException1.getCause();
//        Throwable cause2 = executionException2.getCause();
//
//        assertThrows(ObjectOptimisticLockingFailureException.class, () -> { throw cause1; });
//        assertThrows(ObjectOptimisticLockingFailureException.class, () -> { throw cause2; });
    }

    @Test
    @DisplayName("동시 재고 차감 테스트")
    @Transactional
    void testConcurrentStockDeduction2() throws InterruptedException {
        // Given
        Product product = productFixture.add_usable_product();
        ProductOption option = productFixture.add_usable_product_option(product).get(0);
        productFixture.add_product_stock(product, option, 100);

        // CountDownLatch to coordinate threads
        CountDownLatch latch = new CountDownLatch(2);

        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Callable task to be executed by multiple threads
        Callable<Void> task = () -> {
            try {
                productStockServiceImpl.deductProductStock(product.getProductId(), option.getProductOptionId(), 1);
            } catch (ObjectOptimisticLockingFailureException e) {
                // Expected exception due to optimistic locking
                return null;
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

        // Wait for both threads to finish
        latch.await();
        executorService.shutdown();

        // Check results
        Throwable exception1 = null;
        Throwable exception2 = null;

        try {
            future1.get();
        } catch (ExecutionException e) {
            exception1 = e.getCause();
        }

        try {
            future2.get();
        } catch (ExecutionException e) {
            exception2 = e.getCause();
        }

        // Verify that one threw ObjectOptimisticLockingFailureException and the other returned null
        assertTrue(
                (exception1 == null && exception2 instanceof ObjectOptimisticLockingFailureException) ||
                        (exception2 == null && exception1 instanceof ObjectOptimisticLockingFailureException),
                "One task should have thrown ObjectOptimisticLockingFailureException while the other should have returned null."
        );

    }


    @Test
    @DisplayName("동시 재고 차감 테스트 (10 스레드) ---")
    void testConcurrentStockDeductionWithTenThreads() throws InterruptedException, ExecutionException {
        // Given
        Product product = productFixture.add_usable_product();
        ProductOption option = productFixture.add_usable_product_option(product).get(0);
        productFixture.add_product_stock(product, option, 100);

        // Get ProductStock from repository
        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(product.getProductId(), option.getProductOptionId());

        // CountDownLatch to coordinate threads
        CountDownLatch latch = new CountDownLatch(10);

        // Create a thread pool with 10 threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Callable task to be executed by multiple threads
        Callable<Void> task = () -> {
            try {
                productStockServiceImpl.deductProductStock(product.getProductId(), option.getProductOptionId(), 1);
            } catch (ObjectOptimisticLockingFailureException e) {
                // Expected exception due to optimistic locking
                System.out.println("Exception caught in thread: " + e.getMessage());
                throw e; // Ensure the exception is propagated
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                latch.countDown();
            }
            return null;
        };

        // Submit tasks
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(task));
        }

        // Wait for all threads to finish
        latch.await();

        // Shutdown executor service
        executorService.shutdown();

        // Verify that at least one task failed with an ObjectOptimisticLockingFailureException
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

        // Assert that at least one of the tasks threw an ObjectOptimisticLockingFailureException
        assertTrue(anyExceptionOccurred, "At least one task should have thrown ObjectOptimisticLockingFailureException.");
    }
}
