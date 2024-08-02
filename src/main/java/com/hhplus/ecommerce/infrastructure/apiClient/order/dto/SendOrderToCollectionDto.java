package com.hhplus.ecommerce.infrastructure.apiClient.order.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record SendOrderToCollectionDto(
        String orderNumber,
        int totalPrice,
        LocalDateTime createDatetime
) implements Serializable {
}
