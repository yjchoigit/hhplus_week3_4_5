package com.hhplus.ecommerce.facade.cart;

import com.hhplus.ecommerce.controller.cart.dto.AddCartApiReqDto;
import com.hhplus.ecommerce.controller.cart.dto.FindCartApiResDto;
import com.hhplus.ecommerce.domain.cart.entity.Cart;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.ecommerce.facade.cart.dto.FindCartResDto;
import com.hhplus.ecommerce.service.cart.CartService;
import com.hhplus.ecommerce.service.product.ProductService;
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

    public List<FindCartResDto> findCartList(Long buyerId) {
        List<FindCartResDto> result = new ArrayList<>();

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
                result.add(FindCartResDto.from(cart, product, productOption));
            }
            result.add(FindCartResDto.from(cart, product, null));
        }
        return result;
    }

    @Transactional(rollbackFor = {Exception.class})
    public Cart addCart(Long buyerId, AddCartApiReqDto reqDto){
        // 상품 정보 조회
        productService.findProductByProductId(reqDto.productId());

        // 상품 옵션 id 존재 시 상품 옵션 정보 조회
        if(reqDto.productOptionId() != null){
            productService.findProductOptionByProductIdAndProductOptionId(reqDto.productId(), reqDto.productOptionId());
        }

        return cartService.addCart(Cart.builder()
                .buyerId(buyerId)
                .productId(reqDto.productId())
                .productOptionId(reqDto.productOptionId())
                .buyCnt(reqDto.buyCnt())
                .build());
    }
}
