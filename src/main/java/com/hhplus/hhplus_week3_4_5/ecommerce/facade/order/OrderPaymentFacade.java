package com.hhplus.hhplus_week3_4_5.ecommerce.facade.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.GetOrderApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.apiClient.order.OrderCollectApiClient;
import com.hhplus.hhplus_week3_4_5.ecommerce.infrastructure.apiClient.order.dto.SendOrderToCollectionDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.order.OrderService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.point.PointService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductStockService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class OrderPaymentFacade {
    private OrderService orderService;
    private PointService pointService;
    private ProductService productService;
    private ProductStockService productStockService;
    private OrderCollectApiClient orderCollectApiClient;

    @Transactional(rollbackFor = {Exception.class})
    public Long createOrder(CreateOrderApiReqDto reqDto){
        // 상품 프로세스 진행 (상품 valid)
        productProcess(reqDto);
        // 재고 프로세스 진행 (재고 valid, 재고 차감처리)
        stockProcess(reqDto);
        // 잔액 프로세스 진행 (잔액 valid, 잔액 사용처리)
        pointProcess(reqDto);

        // 주문 생성 진행
        Long orderId = orderService.createOrder(reqDto);
        // 주문 정보 조회
        GetOrderApiResDto orderInfo = orderService.findOrder(reqDto.buyerId(), orderId);

        // 주문 데이터 수집 외부 데이터 플랫폼 전달
        sendOrderToCollection(new SendOrderToCollectionDto(orderInfo.orderNumber(), orderInfo.totalPrice(), orderInfo.createDatetime()));

        return orderInfo.orderId();
    }

    private void productProcess(CreateOrderApiReqDto reqDto){
        for(CreateOrderApiReqDto.CreateOrderItemApiReqDto item : reqDto.orderItemList()){
            Long productId = item.productId();
            Long productOptionId = item.productOptionId();
            
            // 상품 정보 조회
            productService.findProductByProductId(productId);
            
            // 상품 옵션 id 존재 시 상품 옵션 정보 조회
            if(productOptionId != null){
               productService.findProductOptionByProductIdAndProductOptionId(productId, productOptionId);
            }
        }
    }

    private void stockProcess(CreateOrderApiReqDto reqDto){
        for(CreateOrderApiReqDto.CreateOrderItemApiReqDto item : reqDto.orderItemList()) {
            Long productId = item.productId();
            Long productOptionId = item.productOptionId();
            int buyCnt = item.buyCnt();

            productStockService.deductProductStock(productId, productOptionId, buyCnt);
        }
    }

    private void pointProcess(CreateOrderApiReqDto reqDto) {
        // 잔액 확인
        int allPoint = pointService.findPoint(reqDto.buyerId());
        if(allPoint == 0 || allPoint < reqDto.totalPrice()) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        // 잔액 사용처리
        pointService.usePoint(reqDto.buyerId(), reqDto.totalPrice());
    }

    // 주문 데이터 수집 외부 데이터 플랫폼 전달
    private void sendOrderToCollection(SendOrderToCollectionDto sendDto){
        orderCollectApiClient.sendOrderToCollectionPlatform(sendDto);
    }
}
