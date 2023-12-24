package com.coding.template.common;

public enum ErrorCode {
    Success(20000,"成功"),
    PARAMS_ERROR(40000,"请求参数错误"),

    LOGIN_FAILED(40001,"登陆失败"),

    NOT_LOGIN(40003,"当前未登录"),

    SYSTEM_ERROR(50000,"系统错误"),

    REGISTER_FAILED(40002,"注册失败"),

    NOT_FOUND_ERROR(40005, "未查找到用户");

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
