package com.coding.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coding.template.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-10-24 14:05:45
*/
public interface UserService extends IService<User> {

    User userLogin(String userAccount, String password, HttpServletRequest request);

    long userRegister(String userAccount, String password, String checkCode);
}
