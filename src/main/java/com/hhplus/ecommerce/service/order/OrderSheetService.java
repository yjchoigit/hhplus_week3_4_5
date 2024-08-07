package com.hhplus.ecommerce.service.order;

import com.hhplus.ecommerce.domain.order.entity.OrderSheet;
import com.hhplus.ecommerce.service.order.dto.CreateOrderSheetReqDto;
import com.hhplus.ecommerce.service.order.dto.CreateOrderSheetResDto;

public interface OrderSheetService {
    // 주문서 생성
    CreateOrderSheetResDto createOrderSheet(CreateOrderSheetReqDto reqDto);

    // 주문서 조회
    OrderSheet findOrderSheet(Long orderSheetId);

    // 주문서 삭제
    void delOrderSheet(Long orderSheetId);
}
