package com.hhplus.ecommerce.service.cart;

import com.hhplus.ecommerce.domain.cart.entity.Cart;

import java.util.List;

public interface CartService {
    // 장바구니 목록 조회
    List<Cart> findCartList(Long buyerId);

    // 장바구니 추가
    Cart addCart(Cart cart);

    // 장바구니 삭제
    void delCart(Long buyerId, List<Long> cartIdList);
}
