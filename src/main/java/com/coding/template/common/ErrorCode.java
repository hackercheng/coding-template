package com.coding.template.common;

public enum ErrorCode {
    Success(200,"成功"),
    PARAMS_ERROR(400,"请求参数错误"),

    LOGIN_FAILED(401,"登陆失败"),

    REGISTER_FAILED(402,"注册失败");

    private final Integer code;

    private final String message;


    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
