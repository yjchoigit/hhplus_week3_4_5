package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto.CreateOrderSheetApiReqDto;

public interface OrderSheetService {
    Long createOrderSheet(CreateOrderSheetApiReqDto reqDto);
}
