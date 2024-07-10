package com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entities;

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
public class Cart extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("장바구니 id")
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    @Comment("회원 id")
    private Buyer buyer;

    @Column(nullable = false)
    @Comment("상품 id")
    private Long productId;

    @Comment("상품 옵션 id")
    private Long productOptionId;

    @Column(nullable = false)
    @Comment("구매 수량")
    private int buyCnt;

    @Builder
    public Cart(Long cartId, Buyer buyer, Long productId, Long productOptionId, int buyCnt) {
        this.cartId = cartId;
        this.buyer = buyer;
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
