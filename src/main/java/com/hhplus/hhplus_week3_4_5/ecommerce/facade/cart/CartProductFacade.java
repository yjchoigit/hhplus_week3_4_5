package com.hhplus.hhplus_week3_4_5.ecommerce.facade.cart;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.AddCartApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.GetCartApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entity.Cart;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.Product;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.cart.CartService;
import com.hhplus.hhplus_week3_4_5.ecommerce.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class CartProductFacade {
    private final CartService cartService;
    private final ProductService productService;

    public List<GetCartApiResDto> findCartList(Long buyerId) {
        List<GetCartApiResDto> result = new ArrayList<>();

        // 장바구니 목록 조회
        List<Cart> cartList = cartService.findCartList(buyerId);
        if(cartList.isEmpty()){
            return result;
        }

        // 장바구니 목록 내 상품 정보 조회
        for(Cart cart : cartList){
            Long productId = cart.getProductId();
            Long productOptionId = cart.getProductOptionId();

            // 상품 정보 조회
            Product product = productService.findProductByProductId(productId);

            // 상품 옵션 id 존재 시 상품 옵션 정보 조회
            if(productOptionId != null){
                ProductOption productOption = productService.findProductOptionByProductIdAndProductOptionId(productId, productOptionId);
                result.add(GetCartApiResDto.from(cart, product, productOption));
            }
            result.add(GetCartApiResDto.from(cart, product, null));
        }
        return result;
    }

    @Transactional(rollbackFor = {Exception.class})
    public Long addCart(Long buyerId, AddCartApiReqDto reqDto){
        // 상품 정보 조회
        productService.findProductByProductId(reqDto.productId());

        // 상품 옵션 id 존재 시 상품 옵션 정보 조회
        if(reqDto.productOptionId() != null){
            productService.findProductOptionByProductIdAndProductOptionId(reqDto.productId(), reqDto.productOptionId());
        }

        Cart cart = cartService.addCart(Cart.builder()
                .buyerId(buyerId)
                .productId(reqDto.productId())
                .productOptionId(reqDto.productOptionId())
                .buyCnt(reqDto.buyCnt())
                .build());
        return cart.getCartId();
    }
}
