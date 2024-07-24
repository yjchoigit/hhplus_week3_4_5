package com.hhplus.hhplus_week3_4_5.ecommerce.service.order;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderPayment;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.exception.OrderCustomException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderPaymentRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class OrderPaymentServiceImpl implements OrderPaymentService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OrderPaymentRepository orderPaymentRepository;

    @Override
    public Long orderPayment(Long buyerId, Long orderId) {
        // 주문 조회
        Order order = orderRepository.findByBuyerIdAndOrderId(buyerId, orderId);
        if(order == null) {
            throw new OrderCustomException(OrderEnums.Error.NO_ORDER);
        }

        OrderPayment orderPayment = orderPaymentRepository.findByOrderId(orderId);
        // 결제 대기 상태인 경우 -> 결제 처리
        if(OrderEnums.PaymentStatus.WAIT.equals(orderPayment.getStatus())){
            orderPaymentRepository.save(OrderPayment.builder()
                            .order(order)
                            .status(s)
                    .build());
        }

        return 0;
    }
}
