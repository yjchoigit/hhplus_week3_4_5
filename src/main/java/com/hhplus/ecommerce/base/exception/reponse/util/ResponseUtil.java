package com.hhplus.ecommerce.base.exception.reponse.util;

import com.hhplus.ecommerce.base.exception.reponse.BaseEnums;
import com.hhplus.ecommerce.base.exception.reponse.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseUtil {

    public static <T>ResponseDto<T> success(T data) {
        logResponse(BaseEnums.ResponseStatus.SUCCESS, null, null, data);
        return new ResponseDto<>(BaseEnums.ResponseStatus.SUCCESS, null, data);
    }

    public static <T>ResponseDto<T> success() {
        logResponse(BaseEnums.ResponseStatus.SUCCESS, null, null, null);
        return new ResponseDto<>(BaseEnums.ResponseStatus.SUCCESS, null, null);
    }

    public static <T>ResponseDto<T> failure(String code, String message) {
        logResponse(BaseEnums.ResponseStatus.FAILURE, code, message, null);
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, new ResponseDto.Error(code, message), null);
    }

    public static <T>ResponseDto<T> failure(T data) {
        logResponse(BaseEnums.ResponseStatus.FAILURE, null, null, data);
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, null, data);
    }

    public static <T>ResponseDto<T> failure() {
        logResponse(BaseEnums.ResponseStatus.FAILURE, null, null, null);
        return new ResponseDto<>(BaseEnums.ResponseStatus.FAILURE, null, null);
    }

    private static <T> void logResponse(BaseEnums.ResponseStatus status, String code, String message, T data) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"status\": \"").append(status.getCode()).append("\", ");
        sb.append("\"error\": {");
        if (code != null) {
            sb.append("\"errorCode\": \"").append(code).append("\", ");
        }
        if (message != null) {
            sb.append("\"message\": \"").append(message).append("\"");
        }
        sb.append("}");
        sb.append(", ");
        sb.append("\"data\": ").append(data == null ? "null" : "\"" + data.toString() + "\"");
        sb.append("}");
        log.info(sb.toString());
    }

}