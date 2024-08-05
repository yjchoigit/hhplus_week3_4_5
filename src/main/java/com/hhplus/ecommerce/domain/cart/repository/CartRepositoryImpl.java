package com.hhplus.ecommerce.domain.cart.repository;

import com.hhplus.ecommerce.domain.cart.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final CartJpaRepository cartJpaRepository;

    public CartRepositoryImpl(CartJpaRepository cartJpaRepository) {
        this.cartJpaRepository = cartJpaRepository;
    }

    @Override
    public List<Cart> findCartListByBuyerId(Long buyerId) {
        return cartJpaRepository.findAllByBuyerId(buyerId);
    }

    @Override
    public List<Cart> findCartListByBuyerIdAndCartIdList(Long buyerId, List<Long> cartIdList) {
        return cartJpaRepository.findAllByBuyerIdAndCartIdIn(buyerId, cartIdList);
    }

    @Override
    public Cart save(Cart cart) {
        return cartJpaRepository.save(cart);
    }

    @Override
    public void delete(List<Long> cartIdList) {
        cartJpaRepository.deleteAllById(cartIdList);
    }
}
