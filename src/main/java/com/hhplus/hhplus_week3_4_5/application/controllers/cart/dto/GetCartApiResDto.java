package com.hhplus.hhplus_week3_4_5.application.controllers.cart.dto;

import java.io.Serializable;

public record GetCartApiResDto(
    Long cartId,
    Long productId,
    Long productOptionId,
    int buyCnt
) implements Serializable {
}
