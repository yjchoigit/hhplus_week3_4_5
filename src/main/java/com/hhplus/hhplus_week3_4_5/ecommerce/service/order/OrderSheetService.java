package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderSheet;

public interface OrderSheetService {
    CreateOrderSheetApiResDto createOrderSheet(CreateOrderSheetApiReqDto reqDto);
    OrderSheet findOrderSheet(Long orderSheetId);
    void completeOrderSheet(Long orderSheetId);
}
