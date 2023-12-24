package com.coding.template.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRquest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private Integer userRole;

    private static final long serialVersionUID = 1L;
}
