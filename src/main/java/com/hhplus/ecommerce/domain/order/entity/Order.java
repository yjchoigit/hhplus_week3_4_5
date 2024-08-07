package com.hhplus.ecommerce.domain.order.entity;

import com.hhplus.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
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
@Table(name = "`order`")
public class Order extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("주문 id")
    private Long orderId;

    @Column(nullable = false)
    @Comment("주문서 id")
    private Long orderSheetId;

    @Column(nullable = false)
    @Comment("주문 번호")
    private String orderNumber;

    @Column(nullable = false)
    @Comment("회원 id")
    private Long buyerId;

    @Column(nullable = false)
    @Comment("주문자명")
    private String buyerName;

    @Column(nullable = false)
    @Comment("총 구매 수량")
    private int allBuyCnt;

    @Column(nullable = false)
    @Comment("총 상품 가격")
    private int totalPrice;

    public Order(Long orderId, Long orderSheetId, String orderNumber, Long buyerId, String buyerName, int allBuyCnt, int totalPrice) {
        this.orderId = orderId;
        this.orderSheetId = orderSheetId;
        this.orderNumber = orderNumber;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.allBuyCnt = allBuyCnt;
        this.totalPrice = totalPrice;
    }

    @Builder
    public Order(Long orderSheetId, String orderNumber, Long buyerId, String buyerName, int allBuyCnt, int totalPrice) {
        this.orderSheetId = orderSheetId;
        this.orderNumber = orderNumber;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.allBuyCnt = allBuyCnt;
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getOrderId(), order.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderId());
    }
}
