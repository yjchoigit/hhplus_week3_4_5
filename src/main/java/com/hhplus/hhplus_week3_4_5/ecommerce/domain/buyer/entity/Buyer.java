package com.hhplus.hhplus_week3_4_5.ecommerce.domain.buyer.entity;

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
public class Buyer extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("회원 id")
    private Long buyerId;

    @Column(nullable = false, length = 20)
    @Comment("회원명")
    private String name;

    @Builder
    public Buyer(Long buyerId, String name) {
        this.buyerId = buyerId;
        this.name = name;
    }

    public Buyer(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(getBuyerId(), buyer.getBuyerId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getBuyerId());
    }
}
