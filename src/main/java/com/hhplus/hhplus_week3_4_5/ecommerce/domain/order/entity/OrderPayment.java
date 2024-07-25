package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.OrderEnums;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderPayment extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("주문 결제 id")
    private Long orderPaymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Comment("주문 id")
    private Order order;

    @Column(nullable = false)
    @Comment("결제 금액")
    private int paymentPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("결제 상태 Enum")
    private OrderEnums.PaymentStatus status;

    public OrderPayment(Long orderPaymentId, Order order, int paymentPrice, OrderEnums.PaymentStatus status) {
        this.orderPaymentId = orderPaymentId;
        this.order = order;
        this.paymentPrice = paymentPrice;
        this.status = status;
    }

    @Builder
    public OrderPayment(Order order, int paymentPrice, OrderEnums.PaymentStatus status) {
        this.order = order;
        this.paymentPrice = paymentPrice;
        this.status = status;
    }
    
    // 결제 완료 상태로 변경
    public void paymentComplete(){
        this.status = OrderEnums.PaymentStatus.PAY_COMPLETE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPayment that = (OrderPayment) o;
        return Objects.equals(getOrderPaymentId(), that.getOrderPaymentId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderPaymentId());
    }
}
