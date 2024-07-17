package com.hhplus.hhplus_week3_4_5.ecommerce.controller.base.reponse.util;

import com.hhplus.hhplus_week3_4_5.ecommerce.controller.base.BaseEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.controller.base.reponse.dto.ResponseDto;

public class ResponseUtil {

    public static <T>ResponseDto<T> success(T data) {
        return new ResponseDto<>(BaseEnums.ResponseStatus.SUCCESS, null, data);
    }

    public static <T>ResponseDto<T> success() {
        return new ResponseDto<>(BaseEnums.ResponseStatus.SUCCESS, null, null);
    }

    public static <T>ResponseDto<T> failure(String message, T data) {
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, message, data);
    }

    public static <T>ResponseDto<T> failure(T data) {
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, null, data);
    }

    public static <T>ResponseDto<T> failure() {
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, null, null);
    }

    public static <T>ResponseDto<T> error(String message, T data) {
        return new ResponseDto<>(BaseEnums.ResponseStatus.ERROR, message, data);
    }

    public record Error(

    ){

    }
}