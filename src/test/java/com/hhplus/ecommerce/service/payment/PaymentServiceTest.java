package com.hhplus.ecommerce.service.payment;

import com.hhplus.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.ecommerce.domain.order.OrderEnums;
import com.hhplus.ecommerce.domain.order.entity.Order;
import com.hhplus.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.ecommerce.domain.payment.entity.Payment;
import com.hhplus.ecommerce.domain.order.exception.OrderCustomException;
import com.hhplus.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.ecommerce.domain.payment.repository.PaymentRepository;
import com.hhplus.ecommerce.domain.order.repository.OrderRepository;
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
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl orderPaymentServiceImpl;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("주문 결제 처리 성공")
    void pay_success(){
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

        Payment payment = new Payment(1L, order, reqDto.totalPrice(), OrderEnums.PaymentStatus.WAIT);

        // when
        when(orderRepository.findByBuyerIdAndOrderId(1L, 1L)).thenReturn(order);
        when(orderItemRepository.findByOrderId(1L)).thenReturn(List.of(orderItem));
        when(paymentRepository.findByOrderId(1L)).thenReturn(payment);

        Payment result = orderPaymentServiceImpl.pay(1L, 1L);

        // then
        assertNotNull(result);
    }


    @Test
    @DisplayName("주문 결제 처리 실패 - 주문 정보가 없을 때")
    void pay_info_fail(){
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
            orderPaymentServiceImpl.pay(1L, 1L);
        });
    }

    @Test
    @DisplayName("주문 결제 처리 실패 - 이미 결제가 완료된 주문일 때")
    void pay_already_pay_complete_fail(){
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

        Payment payment = new Payment(1L, order, reqDto.totalPrice(), OrderEnums.PaymentStatus.PAY_COMPLETE);

        // when
        when(orderRepository.findByBuyerIdAndOrderId(1L, 1L)).thenReturn(order);
        when(orderItemRepository.findByOrderId(1L)).thenReturn(List.of(orderItem));
        when(paymentRepository.findByOrderId(1L)).thenReturn(payment);

        // then
        assertThrows(OrderCustomException.class, ()-> {
            orderPaymentServiceImpl.pay(1L, 1L);
        });
    }
}
