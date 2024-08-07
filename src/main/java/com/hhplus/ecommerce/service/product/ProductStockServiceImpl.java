package com.hhplus.ecommerce.service.product;

import com.hhplus.ecommerce.base.config.redis.RedisCustomException;
import com.hhplus.ecommerce.base.config.redis.RedisEnums;
import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.ecommerce.domain.product.exception.ProductCustomException;
import com.hhplus.ecommerce.domain.product.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
@Slf4j
public class ProductStockServiceImpl implements ProductStockService {
    private ProductStockRepository productStockRepository;
    private RedissonClient redissonClient;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId) {
        ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
        if(productStock == null) {
            throw new ProductCustomException(ProductEnums.Error.NO_PRODUCT_STOCK);
        }

        return productStock;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ProductStock deductProductStock(Long productId, Long productOptionId, int buyCnt) {
        // productId, productOptionId 기준으로 Lock 객체를 가져옴
        RLock rLock = redissonClient.getLock(RedisEnums.LockName.DEDUCT_PRODUCT.changeLockName(productId, productOptionId));
        boolean isLocked = false;

        try {
            // 락의 이름으로 RLock 인스턴스 가져옴
            log.info("try lock: {}", rLock.getName());
            isLocked = rLock.tryLock(0,  3, TimeUnit.SECONDS);

            // 락을 획득하지 못했을 떄
            if (!isLocked) {
                throw new RedisCustomException(RedisEnums.Error.LOCK_NOT_ACQUIRE);
            }

            // 상품 재고 조회
            ProductStock productStock = productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
            if(productStock == null) {
                throw new ProductCustomException(ProductEnums.Error.NO_PRODUCT_STOCK);
            }
            // 상품 재고 valid
            productStock.validate(buyCnt);

            // 상품 재고 차감 처리
            productStock.deduct(buyCnt);

            return productStock;
        } catch (InterruptedException e) {
            throw new RedisCustomException(RedisEnums.Error.LOCK_INTERRUPTED_ERROR);
        } finally {
            if (isLocked) {
                try{
                    if (rLock.isHeldByCurrentThread()) {
                        rLock.unlock();
                        log.info("unlock complete: {}", rLock.getName());
                    }
                }catch (IllegalMonitorStateException e){
                    //이미 종료된 락일 때 발생하는 예외
                    throw new RedisCustomException(RedisEnums.Error.UNLOCKING_A_LOCK_WHICH_IS_NOT_LOCKED);
                }
            }
        }
    }

}
