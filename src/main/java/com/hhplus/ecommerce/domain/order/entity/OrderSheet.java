package com.hhplus.ecommerce.domain.order.entity;

import com.hhplus.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
import com.hhplus.ecommerce.domain.base.entity.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderSheet extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("주문서 id")
    private Long orderSheetId;

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

    @Comment("주문서 만료일")
    private LocalDateTime expireDatetime;

    @Convert(converter = StringListConverter.class)
    @Comment("장바구니 ID 리스트")
    List<String> cartIdList;

    @Builder
    public OrderSheet(Long buyerId, String buyerName, int allBuyCnt, int totalPrice, LocalDateTime expireDatetime,
                      List<String> cartIdList) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.allBuyCnt = allBuyCnt;
        this.totalPrice = totalPrice;
        this.expireDatetime = expireDatetime;
        this.cartIdList = cartIdList;
    }

    public OrderSheet(Long orderSheetId, Long buyerId, String buyerName, int allBuyCnt, int totalPrice,
                      LocalDateTime expireDatetime, List<String> cartIdList) {
        this.orderSheetId = orderSheetId;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.allBuyCnt = allBuyCnt;
        this.totalPrice = totalPrice;
        this.expireDatetime = expireDatetime;
        this.cartIdList = cartIdList;
    }

    public boolean isExpired(){
        return this.expireDatetime.isBefore(LocalDateTime.now()) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSheet orders = (OrderSheet) o;
        return Objects.equals(getOrderSheetId(), orders.getOrderSheetId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrderSheetId());
    }
}
