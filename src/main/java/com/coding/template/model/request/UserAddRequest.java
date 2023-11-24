package com.coding.template.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户账户
     */
    private String userAccount;

    /**
     * 用户角色
     */
    private String userRole;

    private static final long serialVersionUID =1L;
}
