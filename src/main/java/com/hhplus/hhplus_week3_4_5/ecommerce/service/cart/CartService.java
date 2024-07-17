package com.hhplus.hhplus_week3_4_5.ecommerce.service.cart;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.AddCartApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.GetCartApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findCartList(Long buyerId);
    Cart addCart(Cart cart);
    boolean delCart(Long buyerId, List<Long> cartIdList);
}
