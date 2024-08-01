package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.config.cache.CacheConstants;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.FindOrderApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderPayment;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.exception.OrderCustomException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderPaymentRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OrderPaymentRepository orderPaymentRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long createOrder(CreateOrderApiReqDto reqDto) {
        // 주문 등록
        Order order = orderRepository.save(Order.builder()
                        .orderSheetId(reqDto.orderSheetId())
                        .orderNumber(generateOrderNumber())
                        .buyerId(reqDto.buyerId())
                        .buyerName(reqDto.buyerName())
                        .allBuyCnt(reqDto.allBuyCnt())
                        .totalPrice(reqDto.totalPrice())
                .build());

        for(CreateOrderApiReqDto.CreateOrderItemApiReqDto dto : reqDto.orderItemList()) {
            // 주문 품목 등록
            orderItemRepository.save(OrderItem.builder()
                            .order(order)
                            .productId(dto.productId())
                            .productName(dto.productName())
                            .productOptionId(dto.productOptionId())
                            .productOptionName(dto.productOptionName())
                            .productPrice(dto.productPrice())
                            .buyCnt(dto.buyCnt())
                            .status(OrderEnums.Status.WAIT)
                    .build());
        }
        
        // 주문 결제 등록 (상태 - 결제 대기)
        orderPaymentRepository.save(OrderPayment.builder()
                        .order(order)
                        .paymentPrice(reqDto.totalPrice())
                        .status(OrderEnums.PaymentStatus.WAIT)
                .build());

        return order.getOrderId();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public FindOrderApiResDto findOrder(Long buyerId, Long orderId) {
        // 주문 조회
        Order order = orderRepository.findByBuyerIdAndOrderId(buyerId, orderId);
        if(order == null) {
            throw new OrderCustomException(OrderEnums.Error.NO_ORDER);
        }
        // 주문 품목 조회
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        if(orderItemList.isEmpty()) {
            throw new OrderCustomException(OrderEnums.Error.NO_ORDER);
        }

        return FindOrderApiResDto.from(order, orderItemList.stream()
                .map(FindOrderApiResDto.FindOrderItemApiResDto::from).toList());
    }

    @Override
    @Cacheable(value = CacheConstants.ProductGroup.FIND_PRODUCT_RANKING, key = "#rankingType.name()")
    public List<Object[]> findTopProductsByBuyCnt(ProductEnums.Ranking rankingType) {
        LocalDateTime endDatetime = LocalDateTime.now();
        LocalDateTime startDatetime = getStartDatetime(rankingType, endDatetime);
        // 주문 품목 내역에서 startDatetime, endDatetime 기반 가장 많이 팔린 상위 5개 상품 정보
        List<Object[]> top5ProductList = orderItemRepository.findTopProductsByBuyCnt(startDatetime, endDatetime);

        if(top5ProductList.isEmpty()) {
            return new ArrayList<>();
        }
        return top5ProductList;
    }

    private static String generateOrderNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return LocalDateTime.now().format(formatter);
    }

    // 랭킹타입 별 시작일자 구하기
    private LocalDateTime getStartDatetime(ProductEnums.Ranking rankingType, LocalDateTime endDatetime) {
        LocalDateTime startDatetime = null;
        switch (rankingType){
            case THREE_DAY -> startDatetime = endDatetime.minusDays(3);
            case ONE_WEEK -> startDatetime = endDatetime.minusWeeks(1);
            case ONE_MONTH -> startDatetime = endDatetime.minusMonths(1);
        }
        return startDatetime;
    }
}
