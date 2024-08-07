package com.hhplus.ecommerce.service.order.dto;

import com.hhplus.ecommerce.domain.order.entity.Order;
import com.hhplus.ecommerce.domain.order.entity.OrderItem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record FindOrderResDto(
        Long orderId,
        String orderNumber,
        int allBuyCnt,
        int totalPrice,
        LocalDateTime createDatetime,
        List<FindOrderItemDto> orderItemList

) implements Serializable {

    public record FindOrderItemDto(
            Long orderItemId,
            Long productId,
            String productName,
            Long productOptionId,
            String productOptionName,
            int productPrice,
            int buyCnt
    ) implements Serializable {
        public static FindOrderItemDto from(OrderItem orderItem){
            return new FindOrderItemDto(orderItem.getOrderItemId(), orderItem.getProductId(), orderItem.getProductName(),
                    orderItem.getProductOptionId(), orderItem.getProductOptionName(), orderItem.getProductPrice(), orderItem.getBuyCnt());
        }
    }

    public static FindOrderResDto from(Order order, List<FindOrderItemDto> orderItemList){
        return new FindOrderResDto(order.getOrderId(), order.getOrderNumber(), order.getAllBuyCnt(),
                order.getTotalPrice(), order.getCreateDatetime(), orderItemList);
    }

}
