package com.hhplus.ecommerce.controller.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public record PaymentOrderApiReqDto(
    @Schema(description = "회원 ID")
    Long buyerId,
    @Schema(description = "주문 ID")
    Long orderId
) implements Serializable {
}
