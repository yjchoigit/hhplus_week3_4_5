package com.hhplus.ecommerce.domain.order.repository;

import com.hhplus.ecommerce.domain.order.entity.OrderSheet;

public interface OrderSheetRepository {
    OrderSheet save(OrderSheet orderSheet);
    void delete(OrderSheet orderSheet);
    OrderSheet findByOrderSheetId(Long orderSheetId);
    OrderSheet findByBuyerId(Long buyerId);
}
