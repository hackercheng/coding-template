package com.coding.template.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 账户名
     */
    private String userAccount;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户角色(0-管理员，1-用户)
     */
    private Integer userRole;
}
