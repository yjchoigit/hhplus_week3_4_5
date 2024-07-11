package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.GetOrderApiResDto;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Long createOrder(CreateOrderApiReqDto reqDto);
    GetOrderApiResDto findOrder(Long buyerId, Long orderId);
    List<Long> findTopProductsBySales(LocalDateTime startDatetime, LocalDateTime endDatetime);
}
