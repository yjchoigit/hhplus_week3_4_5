package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.FindOrderApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.FindProductRankingApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    Long createOrder(CreateOrderApiReqDto reqDto);
    FindOrderApiResDto findOrder(Long buyerId, Long orderId);
    List<Object[]> findTopProductsByBuyCnt(ProductEnums.Ranking rankingType);
}
