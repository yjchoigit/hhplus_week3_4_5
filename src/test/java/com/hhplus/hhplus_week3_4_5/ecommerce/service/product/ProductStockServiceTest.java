package com.hhplus.hhplus_week3_4_5.ecommerce.service.product;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.ProductEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductStock;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.repository.ProductStockRepository;
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
class ProductStockServiceTest {

    @InjectMocks
    ProductStockServiceImpl productStockServiceImpl;

    @Mock
    private ProductStockRepository productStockRepository;

    private Product product;
    private List<ProductOption> options;
    
    @BeforeEach
    void setUp() {
        // 상품 등록
        product = Product.builder()
                .productId(1L)
                .name("운동화")
                .type(ProductEnums.Type.OPTION)
                .price(3000)
                .useYn(true)
                .delYn(false)
                .build();
        // 상품 옵션 등록
        options = List.of(new ProductOption(1L, product, ProductEnums.OptionType.BASIC, "색깔", "빨강", 3100, true));
    }

    @Test
    @DisplayName("상품 재고 조회 성공")
    void findProductStockByProductIdAndProductOptionId_success() {
        // given
        Long productId = 1L;
        ProductStock productStock = ProductStock.builder()
                .productStockId(1L)
                .product(product)
                .productOption(options.get(0))
                .type(ProductEnums.StockType.OPTION)
                .stock(100)
                .build();
        Long productOptionId = options.get(0).getProductOptionId();

        // when
        when(productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId)).thenReturn(productStock);

        // then
        ProductStock result = productStockServiceImpl.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
        assertEquals(result.getStock(), 100);
    }

    @Test
    @DisplayName("상품 재고 조회 실패 - 재고 정보가 없을 때")
    void findProductStockByProductIdAndProductOptionId_no_info_fail() {
        // given
        Long productId = 1L;
        Long productOptionId = 1L;

        // when
        when(productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId)).thenReturn(null);

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            productStockServiceImpl.findProductStockByProductIdAndProductOptionId(productId, productOptionId);
        });
    }
    
    @Test
    @DisplayName("상품 재고 차감 성공")
    void deductProductStock_success() {
        // given
        Long productId = 1L;
        ProductStock productStock = ProductStock.builder()
                .productStockId(1L)
                .product(product)
                .productOption(options.get(0))
                .type(ProductEnums.StockType.OPTION)
                .stock(100)
                .build();
        Long productOptionId = options.get(0).getProductOptionId();

        // when
        when(productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId)).thenReturn(productStock);

        // then
        boolean result = productStockServiceImpl.deductProductStock(productId, productOptionId, 10);
        assertTrue(result);
    }

    @Test
    @DisplayName("상품 재고 차감 실패 - 재고 정보가 없을 때")
    void deductProductStock_no_info_fail() {
        // given
        Long productId = 1L;
        Long productOptionId = 1L;

        // when
        when(productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId)).thenReturn(null);

        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            productStockServiceImpl.deductProductStock(productId, productOptionId, 10);
        });
    }

    @Test
    @DisplayName("상품 재고 차감 실패 - 재고가 부족할 때")
    void deductProductStock_out_of_stock_fail() {
        // given
        Long productId = 1L;
        ProductStock productStock = ProductStock.builder()
                .productStockId(1L)
                .product(product)
                .productOption(options.get(0))
                .type(ProductEnums.StockType.OPTION)
                .stock(1)
                .build();
        Long productOptionId = options.get(0).getProductOptionId();

        // when
        when(productStockRepository.findProductStockByProductIdAndProductOptionId(productId, productOptionId)).thenReturn(productStock);
        // then
        assertThrows(IllegalArgumentException.class, ()-> {
            productStockServiceImpl.deductProductStock(productId, productOptionId, 10);
        });

    }
}