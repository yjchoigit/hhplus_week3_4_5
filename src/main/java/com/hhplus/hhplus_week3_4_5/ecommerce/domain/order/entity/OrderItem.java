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
public class OrderItem extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("주문 품목 id")
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Comment("주문 id")
    private Order order;

    @Column(nullable = false)
    @Comment("상품 id")
    private Long productId;

    @Column(nullable = false)
    @Comment("상품명")
    private String productName;

    @Comment("상품 옵션 id")
    private Long productOptionId;

    @Comment("상품 옵션명")
    private String productOptionName;

    @Column(nullable = false)
    @Comment("상품 가격")
    private int productPrice;

    @Column(nullable = false)
    @Comment("구매 수량")
    private int buyCnt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("주문 상태 Enum")
    private OrderEnums.Status status;

    public OrderItem(Long orderItemId, Order order, Long productId, String productName, Long productOptionId, String productOptionName, int productPrice, int buyCnt, OrderEnums.Status status) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.productId = productId;
        this.productName = productName;
        this.productOptionId = productOptionId;
        this.productOptionName = productOptionName;
        this.productPrice = productPrice;
        this.buyCnt = buyCnt;
        this.status = status;
    }

    @Builder
    public OrderItem(Order order, Long productId, String productName, Long productOptionId, String productOptionName, int productPrice, int buyCnt, OrderEnums.Status status) {
        this.order = order;
        this.productId = productId;
        this.productName = productName;
        this.productOptionId = productOptionId;
        this.productOptionName = productOptionName;
        this.productPrice = productPrice;
        this.buyCnt = buyCnt;
        this.status = status;
    }

    // 입금 완료 상태로 변경
    public void depositComplete(){
        this.status = OrderEnums.Status.DEPOSIT_COMPLETE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(getOrderItemId(), orderItem.getOrderItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderItemId());
    }
}
