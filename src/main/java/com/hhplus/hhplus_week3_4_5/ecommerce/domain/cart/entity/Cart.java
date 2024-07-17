package com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entity;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
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
public class Cart extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("장바구니 id")
    private Long cartId;

    @Column(nullable = false)
    @Comment("회원 id")
    private Long buyerId;

    @Column(nullable = false)
    @Comment("상품 id")
    private Long productId;

    @Comment("상품 옵션 id")
    private Long productOptionId;

    @Column(nullable = false)
    @Comment("구매 수량")
    private int buyCnt;

    @Builder
    public Cart(Long buyerId, Long productId, Long productOptionId, int buyCnt) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.productOptionId = productOptionId;
        this.buyCnt = buyCnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(getCartId(), cart.getCartId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCartId());
    }
}
