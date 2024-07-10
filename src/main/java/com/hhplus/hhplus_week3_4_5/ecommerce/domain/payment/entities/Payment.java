package com.hhplus.hhplus_week3_4_5.ecommerce.domain.payment.entities;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entities.CreateModifyDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entities.Orders;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.payment.PaymentEnums;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("결제 id")
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Comment("주문 id")
    private Orders orders;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("결제 타입 Enum")
    private PaymentEnums.Type type;

    @Column(nullable = false)
    @Comment("결제 금액")
    private BigDecimal paymentPrice;

    @Column(nullable = false, columnDefinition = "char")
    @Comment("결제 성공 여부")
    private boolean successYn;

    @Builder
    public Payment(Long paymentId, Orders orders, PaymentEnums.Type type, BigDecimal paymentPrice, boolean successYn) {
        this.paymentId = paymentId;
        this.orders = orders;
        this.type = type;
        this.paymentPrice = paymentPrice;
        this.successYn = successYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(getPaymentId(), payment.getPaymentId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPaymentId());
    }
}
