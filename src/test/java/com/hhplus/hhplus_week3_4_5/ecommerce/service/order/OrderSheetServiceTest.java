package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItemSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemSheetRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderSheetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderSheetServiceTest {

    @InjectMocks
    OrderSheetServiceImpl orderSheetServiceImpl;

    @Mock
    private OrderSheetRepository orderSheetRepository;

    @Mock
    private OrderItemSheetRepository orderItemSheetRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("주문서 생성 성공")
    void createOrderSheet_success() {
        // given
        List<CreateOrderSheetApiReqDto.CreateOrderItemSheetApiReqDto> items = List.of(CreateOrderSheetApiReqDto.CreateOrderItemSheetApiReqDto.builder()
                .productId(1L)
                .productName("운동화")
                .productOptionId(1L)
                .productOptionName("색깔/빨강")
                .productPrice(100)
                .buyCnt(10)
                .build());
        CreateOrderSheetApiReqDto reqDto =  CreateOrderSheetApiReqDto.builder()
                .buyerId(1L)
                .buyerName("홍길동")
                .allBuyCnt(10)
                .totalPrice(1000)
                .orderItemList(items)
                .build();

        OrderSheet orderSheet = new OrderSheet(1L, reqDto.buyerId(), reqDto.buyerName(),
                reqDto.allBuyCnt(), reqDto.totalPrice(), LocalDateTime.now().plusHours(3));

        CreateOrderSheetApiReqDto.CreateOrderItemSheetApiReqDto dto = items.get(0);
        OrderItemSheet orderItemSheet = new OrderItemSheet(1L, orderSheet, dto.productId(), dto.productName(),
                dto.productOptionId(), dto.productOptionName(), dto.productPrice(), dto.buyCnt(), OrderEnums.Status.WAIT);

        // when
        when(orderSheetRepository.save(any(OrderSheet.class))).thenReturn(orderSheet);
        when(orderItemSheetRepository.save(any(OrderItemSheet.class))).thenReturn(orderItemSheet);
        when(orderSheetRepository.findByOrderSheetId(anyLong())).thenReturn(orderSheet);
        when(orderItemSheetRepository.findByOrderSheetId(anyLong())).thenReturn(List.of(orderItemSheet));

        CreateOrderSheetApiResDto result = orderSheetServiceImpl.createOrderSheet(reqDto);

        // then
        assertNotNull(result);
        assertEquals(reqDto.orderItemList().size(), result.orderItemList().size());
    }
}