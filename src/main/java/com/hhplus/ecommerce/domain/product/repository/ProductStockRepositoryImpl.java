package com.hhplus.ecommerce.domain.product.repository;

import com.hhplus.ecommerce.domain.product.entity.ProductStock;
import org.springframework.stereotype.Repository;

@Repository
public class ProductStockRepositoryImpl implements ProductStockRepository {
    private final ProductStockJpaRepository productStockJpaRepository;

    public ProductStockRepositoryImpl(ProductStockJpaRepository productStockJpaRepository) {
        this.productStockJpaRepository = productStockJpaRepository;
    }

    @Override
    public ProductStock findProductStockByProductIdAndProductOptionId(Long productId, Long productOptionId) {
        if(productOptionId != null) {
            return productStockJpaRepository.findByProduct_ProductIdAndProductOption_ProductOptionId(productId, productOptionId);
        }
        return productStockJpaRepository.findByProduct_ProductId(productId);
    }

    @Override
    public ProductStock save(ProductStock productStock) {
        return productStockJpaRepository.save(productStock);
    }
}
