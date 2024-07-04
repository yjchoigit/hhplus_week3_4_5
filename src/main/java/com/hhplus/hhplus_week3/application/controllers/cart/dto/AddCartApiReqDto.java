package com.hhplus.hhplus_week3.application.controllers.cart.dto;

import java.io.Serializable;

public record AddCartApiReqDto(
    Long productId,
    Long productOptionId,
    int buyCnt
) implements Serializable {
}
