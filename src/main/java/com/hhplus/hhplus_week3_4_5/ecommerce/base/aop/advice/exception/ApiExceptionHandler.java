package com.hhplus.hhplus_week3_4_5.ecommerce.base.aop.advice.exception;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.BaseException;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.BaseEnums;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.dto.ResponseDto;
import com.hhplus.hhplus_week3_4_5.ecommerce.base.exception.reponse.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";

    /*
        애플리케이션에서 만든 에러처리(RuntimeException)
    */
    @ExceptionHandler(BaseException.class)
    public ResponseDto<Void> baseExceptionHandler(BaseException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), e.getErrorCode(), e.getMessage());
        return ResponseUtil.failure(e.getErrorCode(), e.getMessage());
    }

    /*
        애플리케이션에서 만든 에러처리(RuntimeException)
    */
    @ExceptionHandler(RuntimeException.class)
    public ResponseDto<Void> runtimeExceptionHandler(RuntimeException e) {
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), BaseEnums.ResponseStatus.ERROR.getCode(), e.getMessage());
        return ResponseUtil.failure(BaseEnums.ResponseStatus.ERROR.getCode(), BaseEnums.ResponseStatus.ERROR.getMessage());
    }

    /*
        애플리케이션에서 지정하지 않은 (예상치 못한) 에러처리(Exception)
    */
    @ExceptionHandler(Exception.class)
    public ResponseDto<Void> exceptionHandler(Exception e) {
        log.error(LOG_FORMAT, e.getClass().getSimpleName(), BaseEnums.ResponseStatus.ERROR.getCode(), e.getMessage());
        return ResponseUtil.failure(BaseEnums.ResponseStatus.ERROR.getCode(), BaseEnums.ResponseStatus.ERROR.getMessage());
    }

    /**
     * Spring Validate Exception
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError field = (FieldError) bindingResult.getAllErrors().get(0);
        String errorMessage = MessageFormat.format("{0} - {1}", field.getField(), field.getDefaultMessage());
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), BaseEnums.ResponseStatus.EXCEPTION_VALIDATION.getCode(), errorMessage);
        return ResponseUtil.failure(BaseEnums.ResponseStatus.EXCEPTION_VALIDATION.getCode(), errorMessage);
    }
}
