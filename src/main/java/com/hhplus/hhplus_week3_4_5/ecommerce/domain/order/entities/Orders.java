package com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entities;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entities.CreateModifyDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entities.Buyer;
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
public class Orders extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("주문 id")
    private Long orderId;

    @Column(nullable = false)
    @Comment("주문 번호")
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    @Comment("회원 id")
    private Buyer buyer;

    @Column(nullable = false)
    @Comment("총 구매 수량")
    private int allBuyCnt;

    @Column(nullable = false, columnDefinition = "char")
    @Comment("주문 성공 여부")
    private boolean successYn;

    @Builder
    public Orders(Long orderId, String orderNumber, Buyer buyer, int allBuyCnt, boolean successYn) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.buyer = buyer;
        this.allBuyCnt = allBuyCnt;
        this.successYn = successYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(getOrderId(), orders.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderId());
    }
}
