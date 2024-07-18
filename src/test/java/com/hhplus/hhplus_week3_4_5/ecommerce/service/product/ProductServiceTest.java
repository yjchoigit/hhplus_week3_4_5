package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.point.repository.PointRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductOptionRepository;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductOptionRepository productOptionRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("상품 조회 성공")
    void findProductByProductId_success() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .productId(1L)
                .name("운동화")
                .type(ProductEnums.Type.SINGLE)
                .price(3000)
                .useYn(true)
                .delYn(false)
                .build();

        // when
        when(productRepository.findByProductId(productId)).thenReturn(product);

        // then
        Product result = productServiceImpl.findProductByProductId(productId);
        assertEquals(result.getName(), "운동화");
    }

    @Test
    @DisplayName("상품 조회 실패 - 상품 정보가 없을 때")
    void findProductByProductId_no_info_fail() {
        // given
        Long productId = 1L;

        // when
        when(productRepository.findByProductId(productId)).thenReturn(null);

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            productServiceImpl.findProductByProductId(productId);
        });
    }

    @Test
    @DisplayName("상품 조회 실패 - 삭제된 상품일 때")
    void findProductByProductId_delete_fail() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .productId(1L)
                .name("운동화")
                .type(ProductEnums.Type.SINGLE)
                .price(3000)
                .useYn(true)
                .delYn(true)
                .build();
        // when
        when(productRepository.findByProductId(productId)).thenReturn(product);

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            productServiceImpl.findProductByProductId(productId);
        });
    }

    @Test
    @DisplayName("상품 조회 실패 - 사용 안하는 상품일 때")
    void findProductByProductId_no_use_fail() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .productId(1L)
                .name("운동화")
                .type(ProductEnums.Type.SINGLE)
                .price(3000)
                .useYn(false)
                .delYn(false)
                .build();
        // when
        when(productRepository.findByProductId(productId)).thenReturn(product);

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            productServiceImpl.findProductByProductId(productId);
        });
    }

    @Test
    @DisplayName("상품 옵션 조회 성공")
    void findProductOptionByProductIdAndProductOptionId_success() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .productId(1L)
                .name("운동화")
                .type(ProductEnums.Type.OPTION)
                .price(3000)
                .useYn(true)
                .delYn(false)
                .build();
        // 상품 옵션 등록
        List<ProductOption> options = new ArrayList<>();
        options.add(new ProductOption(1L, product, ProductEnums.OptionType.BASIC, "색깔", "빨강", 3100, true));
        Long productOptionId = options.get(0).getProductOptionId();

        // when
        when(productOptionRepository.findProductOptionByProductIdAndProductOptionId(productId, productOptionId)).thenReturn(options.get(0));

        // then
        ProductOption result = productServiceImpl.findProductOptionByProductIdAndProductOptionId(productId, productOptionId);
        assertEquals(result.getOptionValue(), "빨강");
    }

    @Test
    @DisplayName("상품 옵션 조회 실패 - 옵션 정보가 없을 때")
    void findProductOptionByProductIdAndProductOptionId_no_info_fail() {
        // given
        Long productId = 1L;
        Long productOptionId = 1L;
        // when
        when(productOptionRepository.findProductOptionByProductIdAndProductOptionId(productId, productOptionId)).thenReturn(null);

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            productServiceImpl.findProductOptionByProductIdAndProductOptionId(productId, productOptionId);
        });
    }

    @Test
    @DisplayName("상품 옵션 조회 실패 - 사용 안하는 상품일 때")
    void findProductOptionByProductIdAndProductOptionId_no_use_fail() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .productId(1L)
                .name("운동화")
                .type(ProductEnums.Type.OPTION)
                .price(3000)
                .useYn(true)
                .delYn(false)
                .build();
        // 상품 옵션 등록
        List<ProductOption> options = new ArrayList<>();
        options.add(new ProductOption(1L, product, ProductEnums.OptionType.BASIC, "색깔", "빨강", 3100, false));
        Long productOptionId = options.get(0).getProductOptionId();

        // when
        when(productOptionRepository.findProductOptionByProductIdAndProductOptionId(productId, productOptionId)).thenReturn(options.get(0));

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            productServiceImpl.findProductOptionByProductIdAndProductOptionId(productId, productOptionId);
        });
    }

}