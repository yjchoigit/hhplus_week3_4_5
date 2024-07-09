package com.hhplus.hhplus_week3.application.controllers.cart;

import com.hhplus.hhplus_week3.application.controllers.cart.dto.AddCartApiReqDto;
import com.hhplus.hhplus_week3.application.controllers.cart.dto.GetCartApiResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    // 장바구니 조회 API
    @GetMapping(value = "/carts/{buyerId}")
    public List<GetCartApiResDto> cart(@PathVariable(name = "buyerId") Long buyerId){
        List<GetCartApiResDto> list = new ArrayList<>();
        list.add(new GetCartApiResDto(1L, 1L, 1L, 3));
        return list;
    }

    // 장바구니 추가 API
    @PostMapping(value = "/carts/{buyerId}")
    public Long addCart(@PathVariable(name = "buyerId") Long buyerId, @RequestBody List<AddCartApiReqDto> list){
        return 1L;
    }

    // 장바구니 삭제 API
    @DeleteMapping(value = "/carts/{buyerId}")
    public boolean delCart(@PathVariable(name = "buyerId") Long buyerId, @RequestParam(name = "cartIdList") List<Long> cartIdList){
        return true;
    }
}
