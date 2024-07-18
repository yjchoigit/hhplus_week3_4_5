package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.converter.BooleanToCharConverter;
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
public class ProductOption extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품 옵션 id")
    private Long productOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Comment("상품 id")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("상품 옵션 타입 Enum")
    private ProductEnums.OptionType type;

    @Column(nullable = false)
    @Comment("옵션 명")
    private String optionName;

    @Column(nullable = false)
    @Comment("옵션 값")
    private String optionValue;

    @Column(nullable = false)
    @Comment("옵션 가격")
    private int price;

    @Convert(converter = BooleanToCharConverter.class)
    @Column(nullable = false)
    @Comment("사용 여부")
    private boolean useYn;

    public ProductOption(Long productOptionId, Product product, ProductEnums.OptionType type, String optionName, String optionValue, int price, boolean useYn) {
        this.productOptionId = productOptionId;
        this.product = product;
        this.type = type;
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.price = price;
        this.useYn = useYn;
    }

    @Builder
    public ProductOption(Product product, ProductEnums.OptionType type, String optionName, String optionValue, int price, boolean useYn) {
        this.product = product;
        this.type = type;
        this.optionName = optionName;
        this.optionValue = optionValue;
        this.price = price;
        this.useYn = useYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOption that = (ProductOption) o;
        return Objects.equals(getProductOptionId(), that.getProductOptionId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProductOptionId());
    }

    public void validate() {
        if(!this.isUseYn()) {
            throw new IllegalArgumentException("사용하지 않는 상품 옵션입니다.");
        }
    }

    public String optionStr(){
        return this.optionName + "/" + this.optionValue;
    }
}
