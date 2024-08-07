package com.hhplus.ecommerce.service.order.dto;

import com.hhplus.ecommerce.controller.order.dto.CreateOrderSheetApiResDto;
import com.hhplus.ecommerce.domain.order.entity.OrderItemSheet;
import com.hhplus.ecommerce.domain.order.entity.OrderSheet;

import java.io.Serializable;
import java.util.List;

public record CreateOrderSheetResDto(
        Long orderSheetId,
        Long buyerId,
        String buyerName,
        int allBuyCnt,
        int totalPrice,
        List<CreateOrderItemSheetResDto> orderItemList
) implements Serializable {

    public static CreateOrderSheetResDto from(OrderSheet orderSheet, List<CreateOrderItemSheetResDto> orderItemList) {
        return new CreateOrderSheetResDto(orderSheet.getOrderSheetId(), orderSheet.getBuyerId(), orderSheet.getBuyerName(),
                orderSheet.getAllBuyCnt(), orderSheet.getTotalPrice(), orderItemList);
    }

    public record CreateOrderItemSheetResDto(
            Long orderSheetItemId,
            Long productId,
            String productName,
            Long productOptionId,
            String productOptionName,
            int productPrice,
            int buyCnt
    ) implements Serializable {

        public static CreateOrderItemSheetResDto from(OrderItemSheet orderItemSheet) {
            return new CreateOrderItemSheetResDto(orderItemSheet.getOrderItemSheetId(), orderItemSheet.getProductId(),
                    orderItemSheet.getProductName(), orderItemSheet.getProductOptionId(), orderItemSheet.getProductOptionName(),
                    orderItemSheet.getProductPrice(), orderItemSheet.getBuyCnt());
        }
    }
}