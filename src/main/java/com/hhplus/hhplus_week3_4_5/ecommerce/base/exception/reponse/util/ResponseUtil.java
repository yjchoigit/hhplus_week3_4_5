package com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.util;

import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.BaseEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.dto.ResponseDto;

public class ResponseUtil {

    public static <T>ResponseDto<T> success(T data) {
        return new ResponseDto<>(BaseEnums.ResponseStatus.SUCCESS, null, data);
    }

    public static <T>ResponseDto<T> success() {
        return new ResponseDto<>(BaseEnums.ResponseStatus.SUCCESS, null, null);
    }

    public static <T>ResponseDto<T> failure(String code, String message) {
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, new ResponseDto.Error(code, message), null);
    }

    public static <T>ResponseDto<T> failure(T data) {
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, null, data);
    }

    public static <T>ResponseDto<T> failure() {
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, null, null);
    }

    public static <T>ResponseDto<T> error(String code, String message, T data) {
        return new ResponseDto<>(BaseEnums.ResponseStatus.ERROR, new ResponseDto.Error(code, message), data);
    }


}