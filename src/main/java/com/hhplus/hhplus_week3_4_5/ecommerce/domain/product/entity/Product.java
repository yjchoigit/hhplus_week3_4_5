package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
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
@Builder
public class Product extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품 id")
    private Long productId;

    @Column(nullable = false)
    @Comment("상품 명")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("상품 타입 Enum")
    private ProductEnums.Type type;

    @Column(nullable = false)
    @Comment("상품 가격")
    private int price;

    @Column(nullable = false, columnDefinition = "char")
    @Comment("사용 여부")
    private boolean useYn;

    @Column(nullable = false, columnDefinition = "char")
    @Comment("삭제 여부")
    private boolean delYn;

    public Product(Long productId, String name, ProductEnums.Type type, int price, boolean useYn, boolean delYn) {
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.useYn = useYn;
        this.delYn = delYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getProductId(), product.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProductId());
    }

    public void validate(){
        if(this.isDelYn()){
            throw new IllegalArgumentException("삭제된 상품입니다.");
        }
        if(!this.isUseYn()) {
            throw new IllegalArgumentException("사용하지 않는 상품입니다.");
        }
    }
}
