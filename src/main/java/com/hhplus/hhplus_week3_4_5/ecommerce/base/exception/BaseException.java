package com.hhplus.hhplus_week3_4_5.ecommerce.base.exception;

import java.text.MessageFormat;

public class BaseException extends RuntimeException {

    private final String errorCode;
    private final String message;

    public BaseException(String code, String message) {
        super(message);
        this.errorCode = code;
        this.message = message;
    }

    public BaseException(String code, String message, Object[] messageParameters) {
        super(messageParameters != null ? MessageFormat.format(message, messageParameters) : message);
        this.errorCode = code;
        this.message = messageParameters != null ? MessageFormat.format(message, messageParameters) : message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
