package com.coding.template.controller;

import com.coding.template.common.ErrorCode;
import com.coding.template.common.Result;
import com.coding.template.exception.BusinessException;
import com.coding.template.model.entity.User;
import com.coding.template.model.request.UserLoginRequest;
import com.coding.template.model.request.UserRegisterRequest;
import com.coding.template.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 判断参数是否有值
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户请求体为空");
        }

        // 获取表单数据，并进行校验
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount,password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        // 登录逻辑
        User user = userService.userLogin(userAccount,password,request);
        if (user == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
        return Result.ok(user);
    }

    @PostMapping("/register")
    public Result<Long> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户请求体为空");
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkCode = userRegisterRequest.getCheckCode();
        if (StringUtils.isAnyBlank(userAccount,password,checkCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        long count = userService.userRegister(userAccount,password,checkCode);
        if (count == 0) {
            throw new BusinessException(ErrorCode.REGISTER_FAILED);
        }
        return Result.ok(count);
    }

}
