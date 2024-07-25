package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

public interface OrderPaymentService {
    Long paymentOrder(Long buyerId, Long orderId);
}
