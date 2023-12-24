package com.coding.template.exception;

import com.coding.template.common.ErrorCode;
import com.coding.template.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> businessException(BusinessException e) {
        log.error("Business",e);
        return Result.fail(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeException(RuntimeException e) {
        log.error("RuntimeException",e);
        return Result.fail(ErrorCode.PARAMS_ERROR, "系统错误");
    }
}
