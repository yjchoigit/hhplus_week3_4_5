package com.hhplus.hhplus_week3_4_5.ecommerce.service.cart;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.AddCartApiReqDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.cart.dto.GetCartApiResDto;

import java.util.List;

public interface CartService {
    List<GetCartApiResDto> findCartList(Long buyerId);
    Long addCart(Long buyerId, AddCartApiReqDto reqDto);
    boolean delCart(Long buyerId, List<Long> cartIdList);
}
