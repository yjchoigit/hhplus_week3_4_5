package com.hhplus.ecommerce.service.order;

import com.hhplus.ecommerce.controller.order.dto.CreateOrderApiReqDto;
import com.hhplus.ecommerce.controller.order.dto.FindOrderApiResDto;
import com.hhplus.ecommerce.domain.product.ProductEnums;

import java.util.List;

public interface OrderService {
    Long createOrder(CreateOrderApiReqDto reqDto);
    FindOrderApiResDto findOrder(Long buyerId, Long orderId);
    List<Object[]> findTopProductsByBuyCnt(ProductEnums.Ranking rankingType);
}
