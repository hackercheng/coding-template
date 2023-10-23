package com.coding.template.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * 前后端交互过程中，后端需要将数据按照前端指定的格式去返回数据
 */
@Data
public class BaseResponse<T> implements Serializable {

    // 状态返回码
    private Integer code;

    // 消息提示
    private String message;

    // 数据
    private T data;

    public BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(Integer code, T data) {
        this(code,"",data);
    }
}
