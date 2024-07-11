package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.GetOrderApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Long createOrder(CreateOrderApiReqDto reqDto) {
        // 주문 등록
        Order order = orderRepository.save(null);
        // 주문 품목 등록
        orderItemRepository.save(null);

        return order.getOrderId();
    }

    @Override
    public GetOrderApiResDto findOrder(Long buyerId, Long orderId) {
        // 주문 조회
        Order order = orderRepository.findByBuyerIdAndOrderId(buyerId, orderId);
        if(order == null) {
            throw new IllegalArgumentException("주문 정보가 없습니다.");
        }
        // 주문 품목 조회
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        if(orderItemList.isEmpty()) {
            throw new IllegalArgumentException("주문 정보가 없습니다.");
        }

        return null;
    }

    @Override
    public List<Long> findTopProductsBySales(LocalDateTime startDatetime, LocalDateTime endDatetime) {
        // 주문 품목 내역에서 startDatetime, endDatetime 기반 가장 많이 팔린 상위 5개 상품 정보
        List<Object[]> top5ProductsBySales = orderItemRepository.findTop5ProductsBySales(startDatetime, endDatetime);
        return List.of();
    }
}
