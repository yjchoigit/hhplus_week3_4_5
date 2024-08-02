package com.hhplus.ecommerce.service.order;

import com.hhplus.ecommerce.controller.order.dto.CreateOrderSheetApiReqDto;
import com.hhplus.ecommerce.controller.order.dto.CreateOrderSheetApiResDto;
import com.hhplus.ecommerce.domain.order.entity.OrderSheet;

public interface OrderSheetService {
    CreateOrderSheetApiResDto createOrderSheet(CreateOrderSheetApiReqDto reqDto);
    OrderSheet findOrderSheet(Long orderSheetId);
    void completeOrderSheet(Long orderSheetId);
}
