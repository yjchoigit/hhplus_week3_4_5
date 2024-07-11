package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductOptionRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductOptionRepository productOptionRepository;
    private ProductStockRepository productStockRepository;

    @Override
    public Product findProductByProductId(Long productId) {
        Product product = productRepository.findByProductId(productId);
        if(product == null){
            throw new IllegalArgumentException("상품 정보가 없습니다.");
        }
        // 상품 valid
        product.validate();
        return product;
    }

    @Override
    public ProductOption findProductOptionByProductIdAndProductOptionId(Long productId, Long productOptionId) {
        ProductOption productOption = productOptionRepository.findProductOptionByProductIdAndProductOptionId(productId, productOptionId);
        if(productOption == null){
            throw new IllegalArgumentException("상품 옵션 정보가 없습니다.");
        }

        // 상품 옵션 valid
        productOption.validate();

        return productOption;
    }

    @Override
    public List<ProductOption> findProductOptionListByProductId(Long productId) {
        List<ProductOption> productOptionList = productOptionRepository.findByProductId(productId);
        if(productOptionList.isEmpty()){
            return new ArrayList<>();
        }
        return productOptionList;
    }


}
