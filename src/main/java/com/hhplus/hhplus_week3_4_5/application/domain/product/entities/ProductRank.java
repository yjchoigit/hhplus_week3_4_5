package com.hhplus.hhplus_week3_4_5.application.domain.product.entities;

import com.hhplus.hhplus_week3_4_5.application.domain.base.entities.CreateDateTimeEntity;
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
public class ProductRank extends CreateDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품 랭킹 id")
    private Long productRankId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Comment("상품 id")
    private Product product;

    @Comment("구매 수량")
    private int count;

    @Builder
    public ProductRank(Long productRankId, Product product, int count) {
        this.productRankId = productRankId;
        this.product = product;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRank that = (ProductRank) o;
        return Objects.equals(getProductRankId(), that.getProductRankId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProductRankId());
    }
}
