package com.hhplus.hhplus_week3_4_5.ecommerce.service.cart;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.AddCartApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.GetCartApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entity.Cart;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
            throw new IllegalArgumentException("장바구니 정보가 없습니다.");        }
        cartRepository.delete(cartIdList);
        return true;
    }
}
