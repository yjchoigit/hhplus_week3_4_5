
package com.hhplus.hhplus_week3_4_5.ecommerce.fixture.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductOptionRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductFixture {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductOptionRepository productOptionRepository;
    @Autowired
    private ProductStockRepository productStockRepository;

    @Transactional
    public Product add_usable_product(){
        return productRepository.save(new Product("운동화", ProductEnums.Type.OPTION, 1000, true, false));
    }

    public Product add_unusable_product(){
        return productRepository.save(new Product("운동화", ProductEnums.Type.OPTION, 1000, false, false));
    }

    @Transactional
    public List<ProductOption> add_usable_product_option(Product product){
        productOptionRepository.save(new ProductOption(product, ProductEnums.OptionType.BASIC, "색깔", "빨강", 1300, true));

        return productOptionRepository.findByProductId(product.getProductId());
    }

    @Transactional
    public ProductStock add_product_stock(Product product, ProductOption productOption, int stock){
        return productStockRepository.save(new ProductStock(product, productOption, ProductEnums.StockType.OPTION, stock));
    }

}
