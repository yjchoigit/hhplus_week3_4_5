package com.hhplus.ecommerce.service.cart;

import com.hhplus.ecommerce.domain.cart.CartEnums;
import com.hhplus.ecommerce.domain.cart.entity.Cart;
import com.hhplus.ecommerce.domain.cart.exception.CartCustomException;
import com.hhplus.ecommerce.domain.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = {Exception.class}, readOnly = true)
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;

    @Override
    public List<Cart> findCartList(Long buyerId) {
        return cartRepository.findCartListByBuyerId(buyerId);
    }

    @Override
    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public boolean delCart(Long buyerId, List<Long> cartIdList) {
        // 장바구니 존재 확인
        List<Cart> cartList = cartRepository.findCartListByBuyerIdAndCartIdList(buyerId, cartIdList);
        if(cartList.isEmpty()){
            throw new CartCustomException(CartEnums.Error.NO_CART);
        }
        cartRepository.delete(cartIdList);
        return true;
    }
}
