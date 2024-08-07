package com.hhplus.ecommerce.fixture.cart;

import com.hhplus.ecommerce.domain.buyer.entity.Buyer;
import com.hhplus.ecommerce.domain.cart.entity.Cart;
import com.hhplus.ecommerce.domain.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartFixture {
    @Autowired
    private CartRepository cartRepository;

    public Cart add_cart(Buyer buyer){
        Cart cart = new Cart(1L, buyer.getBuyerId(), 1L, 1L, 1);
        return cartRepository.save(cart);
    }
}
