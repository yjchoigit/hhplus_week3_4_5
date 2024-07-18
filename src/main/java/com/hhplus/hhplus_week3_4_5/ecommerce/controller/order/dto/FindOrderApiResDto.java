package com.hhplus.hhplus_week3_4_5.ecommerce.controller.order.dto;

import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.Order;
import com.hhplus.hhplus_week3_4_5.ecommerce.domain.order.entity.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record FindOrderApiResDto(
    @Schema(description = "주문 ID")
    Long orderId,
    @Schema(description = "주문번호")
    String orderNumber,
    @Schema(description = "총 구매 수량")
    int allBuyCnt,
    @Schema(description = "총 상품 금액")
    int totalPrice,
    @Schema(description = "주문 일시")
    LocalDateTime createDatetime,
    @Schema(description = "주문 품목 리스트")
    List<FindOrderItemApiResDto> orderItemList

) implements Serializable {
    
    public record FindOrderItemApiResDto(
            @Schema(description = "주문 품목 ID")
            Long orderItemId,
            @Schema(description = "상품 ID")
            Long productId,
            @Schema(description = "상품 명")
            String productName,
            @Schema(description = "상품 옵션 ID")
            Long productOptionId,
            @Schema(description = "상품 옵션 명")
            String productOptionName,
            @Schema(description = "상품 금액")
            int productPrice,
            @Schema(description = "구매 수량")
            int buyCnt
            ) implements Serializable {
        public static FindOrderItemApiResDto from(OrderItem orderItem){
            return new FindOrderItemApiResDto(orderItem.getOrderItemId(), orderItem.getProductId(), orderItem.getProductName(),
                    orderItem.getProductOptionId(), orderItem.getProductOptionName(), orderItem.getProductPrice(), orderItem.getBuyCnt());
        }
    }

    public static FindOrderApiResDto from(Order order, List<FindOrderItemApiResDto> orderItemList){
        return new FindOrderApiResDto(order.getOrderId(), order.getOrderNumber(), order.getAllBuyCnt(),
                order.getTotalPrice(), order.getCreateDatetime(), orderItemList);
    }


}
