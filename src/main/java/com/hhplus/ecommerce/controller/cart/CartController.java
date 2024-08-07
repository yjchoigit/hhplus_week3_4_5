package com.hhplus.ecommerce.controller.cart;

import com.hhplus.ecommerce.base.exception.reponse.dto.ResponseDto;
import com.hhplus.ecommerce.base.exception.reponse.util.ResponseUtil;
import com.hhplus.ecommerce.controller.cart.dto.AddCartApiReqDto;
import com.hhplus.ecommerce.controller.cart.dto.FindCartApiResDto;
import com.hhplus.ecommerce.domain.cart.entity.Cart;
import com.hhplus.ecommerce.facade.cart.CartProductFacade;
import com.hhplus.ecommerce.facade.cart.dto.FindCartResDto;
import com.hhplus.ecommerce.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "/carts", description = "장바구니 API")
@RestController
@RequiredArgsConstructor
@Validated
public class CartController {
    private final CartProductFacade cartProductFacade;
    private final CartService cartService;

    @Operation(summary = "장바구니 목록 조회")
    @ApiResponse(responseCode = "200", description = "성공", content = {@Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = FindCartApiResDto.class))
    )})
    @GetMapping(value = "/carts/{buyerId}")
    public ResponseDto<List<FindCartApiResDto>> findCartList(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId){
        List<FindCartResDto> cartList = cartProductFacade.findCartList(buyerId);
        return ResponseUtil.success(cartList.stream().map(FindCartApiResDto::from).toList());
    }

    @Operation(summary = "장바구니 추가")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)))
    @PostMapping(value = "/carts/{buyerId}")
    public ResponseDto<Long> addCart(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId,
                        @RequestBody @Valid AddCartApiReqDto reqDto){
        Cart cart = cartProductFacade.addCart(buyerId, reqDto);
        if(cart == null) {
            return ResponseUtil.failure();
        }
        return ResponseUtil.success(cart.getCartId());
    }

    @Operation(summary = "장바구니 삭제")
    @ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    @DeleteMapping(value = "/carts/{buyerId}")
    public ResponseDto<Void> delCart(@PathVariable(name = "buyerId") @Schema(description = "회원 ID") @NotNull Long buyerId,
                           @RequestParam(name = "cartIdList") @Schema(description = "장바구니 ID 리스트") @NotNull List<Long> cartIdList){
        cartService.delCart(buyerId, cartIdList);
        return ResponseUtil.success();
    }
}
