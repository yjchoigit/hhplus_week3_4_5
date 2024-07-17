package com.hhplus.hhplus_week3_4_5.ecommerce.service.cart;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.AddCartApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.GetCartApiResDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.entity.Cart;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.cart.repository.CartRepository;
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
    public List<GetCartApiResDto> findCartList(Long buyerId) {
        List<Cart> cartList = cartRepository.findCartListByBuyerId(buyerId);
        return cartList.stream().map(GetCartApiResDto::from).toList();
    }

    @Override
    public Long addCart(Long buyerId, AddCartApiReqDto reqDto) {
        Cart cart = cartRepository.save(Cart.builder()
                        .buyerId(buyerId)
                        .productId(reqDto.productId())
                        .productOptionId(reqDto.productOptionId())
                        .buyCnt(reqDto.buyCnt())
                .build());
        return cart.getCartId();
    }

    @Override
    public boolean delCart(Long buyerId, List<Long> cartIdList) {
        // 장바구니 존재 확인
        List<Cart> cartList = cartRepository.findCartListByBuyerIdAndCartIdList(buyerId, cartIdList);
        if(cartList.isEmpty()){
            return false;
        }
        cartRepository.delete(cartIdList);
        return true;
    }
}
