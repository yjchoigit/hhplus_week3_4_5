package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.config.cache.CacheConstants;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.AddProductApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.FindProductListApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.product.dto.PutProductApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.exception.ProductCustomException;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductOptionRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductOptionRepository productOptionRepository;

    @Override
    @Cacheable(value = CacheConstants.ProductGroup.FIND_PRODUCT_LIST, unless = "#result.isEmpty()")
    public List<FindProductListApiResDto> findProductList() {
        List<Product> list = productRepository.findProductList();
        return list.stream().map(FindProductListApiResDto::from).toList();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    @Cacheable(value = CacheConstants.ProductGroup.FIND_PRODUCT, key = "#productId")
    public Product findProductByProductId(Long productId) {
        Product product = productRepository.findByProductId(productId);
        if(product == null){
            throw new ProductCustomException(ProductEnums.Error.NO_PRODUCT);
        }
        // 상품 valid
        product.validate();
        return product;
    }

    @Override
    public ProductOption findProductOptionByProductIdAndProductOptionId(Long productId, Long productOptionId) {
        ProductOption productOption = productOptionRepository.findProductOptionByProductIdAndProductOptionId(productId, productOptionId);
        if(productOption == null){
            throw new ProductCustomException(ProductEnums.Error.NO_PRODUCT_OPTION);
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

    @Override
    @CacheEvict(value = CacheConstants.ProductGroup.FIND_PRODUCT_LIST, allEntries = true)
    @Transactional(rollbackFor = {Exception.class})
    public Long addProduct(AddProductApiReqDto reqDto) {
        // 상품 등록
        Product product = productRepository.save(Product.builder()
                        .name(reqDto.name())
                        .type(reqDto.type())
                        .price(reqDto.price())
                        .useYn(reqDto.useYn())
                        .delYn(false)
                .build());
        // 상품 옵션 등록
        for(AddProductApiReqDto.AddProductOptionApiReqDto dto : reqDto.optionList()){
            productOptionRepository.save(ProductOption.builder()
                            .product(product)
                            .type(dto.optionType())
                            .optionName(dto.name())
                            .optionValue(dto.value())
                            .price(dto.price())
                            .useYn(dto.useYn())
                    .build());
        }
        return product.getProductId();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.ProductGroup.FIND_PRODUCT_LIST, allEntries = true),
            @CacheEvict(value = CacheConstants.ProductGroup.FIND_PRODUCT, key = "#productId")
    })
    @Transactional(rollbackFor = {Exception.class})
    public Long putProduct(Long productId, PutProductApiReqDto reqDto) {
        // 상품 조회
        Product product = productRepository.findByProductId(productId);
        if(product == null){
            throw new ProductCustomException(ProductEnums.Error.NO_PRODUCT);
        }

        // 상품 정보 수정 (이름, 가격, 사용여부만)
        product.update(Product.builder()
                .name(reqDto.name())
                .price(reqDto.price())
                .useYn(reqDto.useYn())
                .build());

        return product.getProductId();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.ProductGroup.FIND_PRODUCT_LIST, allEntries = true),
            @CacheEvict(value = CacheConstants.ProductGroup.FIND_PRODUCT, key = "#productId")
    })
    @Transactional(rollbackFor = {Exception.class})
    public boolean delProduct(Long productId) {
        // 상품 조회
        Product product = productRepository.findByProductId(productId);
        if(product == null){
            throw new ProductCustomException(ProductEnums.Error.NO_PRODUCT);
        }
        
        // 상품 정보 삭제처리 (삭제여부 - N 처리)
        product.delete();

        return true;
    }


}
