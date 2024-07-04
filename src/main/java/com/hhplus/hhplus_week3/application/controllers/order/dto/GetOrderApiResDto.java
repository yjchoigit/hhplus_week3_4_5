package com.hhplus.hhplus_week3.application.controllers.order.dto;

import com.hhplus.hhplus_week3.application.domain.payment.PaymentEnums;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record GetOrderApiResDto(

    Long memberOrderId,
    String orderNumber,
    int allBuyCnt,
    LocalDateTime createDatetime,
    List<GetOrderDetailApiResDto> detail,
    List<GetOrderPaymentApiResDto> payment

) implements Serializable {

    public record GetOrderDetailApiResDto(
            Long memberOrderDetailId,
            Long productId,
            Long productOptionId,
            int buyCnt

            ) implements Serializable {
    }

    public record GetOrderPaymentApiResDto(
            Long memberOrderPaymentId,
            PaymentEnums.Type type,
            BigDecimal paymentPrice,
            LocalDateTime createDatetime

            ) implements Serializable {
    }
}
