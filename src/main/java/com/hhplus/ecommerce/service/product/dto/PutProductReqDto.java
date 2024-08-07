package com.hhplus.ecommerce.service.product.dto;

import java.io.Serializable;

public record PutProductReqDto(
        String name,
        int price,
        boolean useYn
) implements Serializable {

}
