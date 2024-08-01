package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.converter.BooleanToCharConverter;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.exception.ProductCustomException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@DynamicUpdate
public class Product extends CreateModifyDateTimeEntity implements Serializable {
    private static final long serialVersionUID = 1L; // 직렬화 버전 관리용

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

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    @Comment("사용 여부")
    private boolean useYn;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
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

    public Product(String name, ProductEnums.Type type, int price, boolean useYn, boolean delYn) {
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
            throw new ProductCustomException(ProductEnums.Error.DELETE_PRODUCT);
        }
        if(!this.isUseYn()) {
            throw new ProductCustomException(ProductEnums.Error.UNUSABLE_PRODUCT);
        }
    }

    public void update(Product updateData) {
        this.name = updateData.getName();
        this.price = updateData.getPrice();
        this.useYn = updateData.isUseYn();
    }

    public void delete() {
        this.delYn = false;
    }
}
