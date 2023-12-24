package com.coding.template.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryRequest implements Serializable {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账户名
     */
    private String userAccount;

    /**
     * 页码
     */
    private long current;

    /**
     * 数据量
     */
    private long limit;
}
