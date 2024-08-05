package com.hhplus.ecommerce.service.order;

import com.hhplus.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.ecommerce.controller.order.dto.FindOrderApiResDto;
import com.hhplus.ecommerce.domain.order.OrderEnums;
import com.hhplus.ecommerce.domain.order.entity.Order;
import com.hhplus.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.ecommerce.domain.order.entity.OrderPayment;
import com.hhplus.ecommerce.domain.order.exception.OrderCustomException;
import com.hhplus.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.ecommerce.domain.order.repository.OrderPaymentRepository;
import com.hhplus.ecommerce.domain.order.repository.OrderRepository;
import com.hhplus.ecommerce.domain.product.ProductEnums;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderPaymentRepository orderPaymentRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("주문 생성 성공")
    void createOrder_success() {
        // given
        List<CreateOrderApiReqDto.CreateOrderItemApiReqDto> items = List.of(CreateOrderApiReqDto.CreateOrderItemApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(100)
                .buyCnt(10)
                .build());
        CreateOrderApiReqDto reqDto =  CreateOrderApiReqDto.builder()
                .orderSheetId(1L)
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(10)
                .totalPrice(1000)
                .orderItemList(items)
                .build();

        Order order = new Order(1L, 1L, "20240712000000", reqDto.buyerId(), reqDto.buyerName(),
                reqDto.allBuyCnt(), reqDto.totalPrice());

        CreateOrderApiReqDto.CreateOrderItemApiReqDto dto = items.get(0);
        OrderItem orderItem = new OrderItem(1L, order, dto.productId(), dto.productName(),
                dto.productOptionId(), dto.productOptionName(), dto.productPrice(), dto.buyCnt(), OrderEnums.Status.WAIT);

        OrderPayment orderPayment = new OrderPayment(1L, order, reqDto.totalPrice(), OrderEnums.PaymentStatus.WAIT);

        // when
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        when(orderPaymentRepository.save(any(OrderPayment.class))).thenReturn(orderPayment);

        Long result = orderServiceImpl.createOrder(reqDto);

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("주문 조회 성공")
    void findOrder_success() {
        // given
        Long buyerId = 1L;
        Order order = new Order(1L, 1L,  "20240712000000", 1L, "홍길동",
                10, 1000);

        List<OrderItem> items = List.of(new OrderItem(1L, order, 1L, "운동화",
                1L, "색깔/빨강", 100, 10, OrderEnums.Status.DEPOSIT_COMPLETE));

        // when
        when(orderRepository.findByBuyerIdAndOrderId(anyLong(), anyLong())).thenReturn(order);
        when(orderItemRepository.findByOrderId(anyLong())).thenReturn(items);

        FindOrderApiResDto result = orderServiceImpl.findOrder(buyerId, 1L);

        // then
        assertNotNull(result);
        assertEquals(items.size(), result.orderItemList().size());
    }

    @Test
    @DisplayName("주문 조회 실패 - 주문 정보가 없을 때")
    void findOrder_no_info_fail() {
        // given
        Long buyerId = 1L;
        Order order = new Order(1L, 1L, "20240712000000", 1L, "홍길동",
                10, 1000);

        // when
        when(orderRepository.findByBuyerIdAndOrderId(anyLong(), anyLong())).thenReturn(order);
        when(orderItemRepository.findByOrderId(anyLong())).thenReturn(new ArrayList<>());

        // then
        assertThrows(OrderCustomException.class, ()-> {
            orderServiceImpl.findOrder(buyerId, 1L);
        });
    }

    @Test
    @DisplayName("가장 많이 팔린 상위 5개 상품 정보 조회 성공")
    void findTopProductsByBuyCnt_success() {
        // given
        ProductEnums.Ranking rankingType = ProductEnums.Ranking.THREE_DAY;

        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{1L, 100});
        list.add(new Object[]{2L, 90});
        list.add(new Object[]{3L, 80});
        list.add(new Object[]{4L, 70});
        list.add(new Object[]{5L, 60});

        // when
        when(orderItemRepository.findTopProductsByBuyCnt(LocalDateTime.now().minusDays(3), LocalDateTime.now())).thenReturn(list);

        List<Object[]> result = orderServiceImpl.findTopProductsByBuyCnt(rankingType);

        // then
        assertEquals(list.size(), result.size());
    }
}