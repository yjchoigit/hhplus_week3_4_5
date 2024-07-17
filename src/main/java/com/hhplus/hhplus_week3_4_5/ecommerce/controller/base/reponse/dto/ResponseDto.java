package com.hhplus.hhplus_week3_4_5.ecommerce.controller.base.reponse.dto;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.base.BaseEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private final BaseEnums.ResponseStatus status;
    private final String message;
    private final T data;
}