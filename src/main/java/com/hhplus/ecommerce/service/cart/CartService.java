package com.hhplus.ecommerce.service.cart;

import com.hhplus.ecommerce.domain.cart.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findCartList(Long buyerId);
    Cart addCart(Cart cart);
    boolean delCart(Long buyerId, List<Long> cartIdList);
}
