package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderPayment;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.exception.OrderCustomException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderPaymentRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPaymentServiceTest {

    @InjectMocks
    OrderPaymentServiceImpl orderPaymentServiceImpl;

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
    @DisplayName("주문 결제 처리 성공")
    void paymentOrder_success(){
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
        when(orderRepository.findByBuyerIdAndOrderId(1L, 1L)).thenReturn(order);
        when(orderItemRepository.findByOrderId(1L)).thenReturn(List.of(orderItem));
        when(orderPaymentRepository.findByOrderId(1L)).thenReturn(orderPayment);

        Long result = orderPaymentServiceImpl.paymentOrder(1L, 1L);

        // then
        assertNotNull(result);
    }


    @Test
    @DisplayName("주문 결제 처리 실패 - 주문 정보가 없을 때")
    void paymentOrder_no_order_info_fail(){
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

        // when
        when(orderRepository.findByBuyerIdAndOrderId(1L, 1L)).thenReturn(null);

        // then
        assertThrows(OrderCustomException.class, ()-> {
            orderPaymentServiceImpl.paymentOrder(1L, 1L);
        });
    }

    @Test
    @DisplayName("주문 결제 처리 실패 - 이미 결제가 완료된 주문일 때")
    void paymentOrder_already_pay_complete_fail(){
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
                dto.productOptionId(), dto.productOptionName(), dto.productPrice(), dto.buyCnt(), OrderEnums.Status.DEPOSIT_COMPLETE);

        OrderPayment orderPayment = new OrderPayment(1L, order, reqDto.totalPrice(), OrderEnums.PaymentStatus.PAY_COMPLETE);

        // when
        when(orderRepository.findByBuyerIdAndOrderId(1L, 1L)).thenReturn(order);
        when(orderItemRepository.findByOrderId(1L)).thenReturn(List.of(orderItem));
        when(orderPaymentRepository.findByOrderId(1L)).thenReturn(orderPayment);

        // then
        assertThrows(OrderCustomException.class, ()-> {
            orderPaymentServiceImpl.paymentOrder(1L, 1L);
        });
    }
}
