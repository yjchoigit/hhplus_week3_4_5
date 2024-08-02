package com.hhplus.ecommerce.service.order;

import com.hhplus.ecommerce.domain.order.OrderEnums;
import com.hhplus.ecommerce.domain.order.entity.Order;
import com.hhplus.ecommerce.domain.order.entity.OrderItem;
import com.hhplus.ecommerce.domain.order.entity.OrderPayment;
import com.hhplus.ecommerce.domain.order.exception.OrderCustomException;
import com.hhplus.ecommerce.domain.order.repository.OrderItemRepository;
import com.hhplus.ecommerce.domain.order.repository.OrderPaymentRepository;
import com.hhplus.ecommerce.domain.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class OrderPaymentServiceImpl implements OrderPaymentService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private OrderPaymentRepository orderPaymentRepository;

    @Override
    public Long paymentOrder(Long buyerId, Long orderId) {
        // 주문 조회
        Order order = orderRepository.findByBuyerIdAndOrderId(buyerId, orderId);
        if(order == null) {
            throw new OrderCustomException(OrderEnums.Error.NO_ORDER);
        }
        // 주문 품목 조회
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);
        if(orderItemList.isEmpty()) {
            throw new OrderCustomException(OrderEnums.Error.NO_ORDER);
        }
        
        // 주문 결제 조회
        OrderPayment orderPayment = orderPaymentRepository.findByOrderId(orderId);

        // 결제 상태 확인 -> 결제완료, exception
        if(OrderEnums.PaymentStatus.WAIT.equals(orderPayment.getStatus())){
            // 결제 대기 상태인 경우 -> 결제 완료로 전환
            orderPayment.paymentComplete();
            // 주문 품목 상태 -> 입금 완료로 전환
            orderItemList.forEach(OrderItem::depositComplete);
        } else if (OrderEnums.PaymentStatus.PAY_COMPLETE.equals(orderPayment.getStatus())){
            // 결제 완료 상태인 경우 -> 이미 결제가 완료된 주문
            throw new OrderCustomException(OrderEnums.Error.ALREADY_PAY_COMPLETE_ORDER);
        } else if (OrderEnums.PaymentStatus.REFUND.equals(orderPayment.getStatus())){
            // 결제 환불 상태인 경우 -> 이미 결제가 환불된 주문
            throw new OrderCustomException(OrderEnums.Error.ALREADY_REFUND_ORDER);
        }


        return orderPayment.getOrderPaymentId();
    }
}
