package com.coding.template.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * 前后端交互过程中，后端需要将数据按照前端指定的格式去返回数据
 */
@Data
public class Result<T> implements Serializable {

    // 状态返回码
    private Integer code;

    // 消息提示
    private String message;

    // 数据
    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, T data) {
        this(code, "", data);
    }

    public Result(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> Result<T> ok(Integer code, T data) {
        return new Result<>(code, "ok", data);
    }

    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(errorCode);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> fail(ErrorCode errorCode, String message) {
        return new Result<>(errorCode.getCode(), message, null);
    }
}
