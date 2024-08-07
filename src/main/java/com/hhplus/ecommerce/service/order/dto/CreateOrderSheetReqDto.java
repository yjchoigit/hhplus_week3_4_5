package com.hhplus.ecommerce.service.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record CreateOrderSheetReqDto(
    Long buyerId,
    String buyerName,
    int allBuyCnt,
    int totalPrice,
    List<Long> cartIdList,
    List<CreateOrderItemSheetReqDto> orderItemList
) implements Serializable {

    @Builder
    public record CreateOrderItemSheetReqDto(
            Long productId,
            String productName,
            Long productOptionId,
            String productOptionName,
            int productPrice,
            int buyCnt
    ) implements Serializable {

    }
}
