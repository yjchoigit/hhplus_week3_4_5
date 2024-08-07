package com.hhplus.ecommerce.domain.cart.repository;

import com.hhplus.ecommerce.domain.cart.entity.Cart;

import java.util.List;

public interface CartRepository {
    List<Cart> findCartListByBuyerId(Long buyerId);
    List<Cart> findCartListByBuyerIdAndCartIdList(Long buyerId, List<Long> cartIdList);
    Cart save(Cart cart);
    void delete(List<Long> cartIdList);
}
