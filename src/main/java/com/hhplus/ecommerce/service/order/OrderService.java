package com.hhplus.ecommerce.service.order;

import com.hhplus.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.ecommerce.domain.order.entity.Order;
import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.service.order.dto.FindOrderResDto;

import java.util.List;

public interface OrderService {
    // 주문 생성
    Order createOrder(CreateOrderApiReqDto reqDto);

    // 주문 조회
    FindOrderResDto findOrder(Long buyerId, Long orderId);

    // 상위 상품 조회
    List<Object[]> findTopProductsByBuyCnt(ProductEnums.Ranking rankingType);
}
