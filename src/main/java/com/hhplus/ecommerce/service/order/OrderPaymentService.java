package com.hhplus.ecommerce.service.order;

public interface OrderPaymentService {
    Long paymentOrder(Long buyerId, Long orderId);
}
