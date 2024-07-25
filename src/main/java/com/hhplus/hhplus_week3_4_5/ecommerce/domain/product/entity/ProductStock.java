package com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.base.entity.CreateModifyDateTimeEntity;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.exception.ProductCustomException;
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
public class ProductStock extends CreateModifyDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품 재고 id")
    private Long productStockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Comment("상품 id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    @Comment("상품 옵션 id")
    private ProductOption productOption;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("상품 재고 타입 Enum")
    private ProductEnums.StockType type;

    @Column(nullable = false)
    @Comment("재고 수량")
    private int stock;

    @Version
    private Long version;

    @Builder
    public ProductStock(Long productStockId, Product product, ProductOption productOption, ProductEnums.StockType type, int stock) {
        this.productStockId = productStockId;
        this.product = product;
        this.productOption = productOption;
        this.type = type;
        this.stock = stock;
    }

    public ProductStock(Product product, ProductOption productOption, ProductEnums.StockType type, int stock) {
        this.product = product;
        this.productOption = productOption;
        this.type = type;
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStock that = (ProductStock) o;
        return Objects.equals(getProductStockId(), that.getProductStockId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProductStockId());
    }

    public void validate(int buyCnt) {
        // 재고가 0일 때
        if(this.stock == 0){
            throw new ProductCustomException(ProductEnums.Error.ZERO_PRODUCT_STOCK);
        }
        // 재고가 구매수량보다 적은 경우
        if(this.stock < buyCnt) {
            throw new ProductCustomException(ProductEnums.Error.OUT_OF_PRODUCT_STOCK);
        }
    }

    public void deduct(int buyCnt){
        this.stock -= buyCnt;
    }
}
