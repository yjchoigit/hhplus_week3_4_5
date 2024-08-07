package com.hhplus.ecommerce.service.cart;

import com.hhplus.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.ecommerce.domain.cart.entity.Cart;
import com.hhplus.ecommerce.domain.cart.exception.CartCustomException;
import com.hhplus.ecommerce.domain.cart.repository.CartRepository;
import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartServiceImpl cartServiceImpl;

    @Mock
    private CartRepository cartRepository;

    private Buyer buyer;
    private Cart cart;
    private Product product;
    private ProductOption productOption;

    @BeforeEach
    void setUp() {
        // 회원 등록
        buyer = new Buyer(1L, "홍길동");
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
        List<ProductOption> options = new ArrayList<>();
        options.add(new ProductOption(1L, product, ProductEnums.OptionType.BASIC, "색깔", "빨강", 3100, true));
        productOption = options.get(0);

        // 장바구니 등록
        cart = new Cart(1L, buyer.getBuyerId(), product.getProductId(), productOption.getProductOptionId(), 10);
    }

    @Test
    @DisplayName("장바구니 목록 조회 성공")
    void findCartList_success(){
        // given
        Long buyerId = 1L;

        // when
        when(cartRepository.findCartListByBuyerId(buyerId)).thenReturn(List.of(cart));

        // then
        List<Cart> result = cartServiceImpl.findCartList(buyerId);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("장바구니 추가 성공")
    void addCart_success(){
        // given
        Long buyerId = 1L;

        // when
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        // then
        Cart result = cartServiceImpl.addCart(Cart.builder()
                .buyerId(buyerId)
                .productId(1L)
                .productOptionId(1L)
                .buyCnt(10)
                .build());

        assertEquals(result.getBuyCnt(),10);
    }

    @Test
    @DisplayName("장바구니 삭제 성공")
    void delCart_success(){
        // given
        Long buyerId = 1L;

        // when
        when(cartRepository.findCartListByBuyerIdAndCartIdList(any(), any())).thenReturn(List.of(cart));

        // then
        cartServiceImpl.delCart(buyerId, List.of(1L));

        verify(cartRepository).delete(any());
    }

    @Test
    @DisplayName("장바구니 삭제 실패 - 장바구니 정보가 없을 때")
    void delCart_success_no_product_fail(){
        // given
        Long buyerId = 1L;

        // when
        when(cartRepository.findCartListByBuyerIdAndCartIdList(any(), any())).thenReturn(new ArrayList<>());

        // then
        assertThrows(CartCustomException.class, ()-> {
            cartServiceImpl.delCart(buyerId, List.of(1L));
        });
        verify(cartRepository, never()).delete(any());
    }

}
