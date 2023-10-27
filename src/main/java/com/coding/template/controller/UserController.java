package com.coding.template.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.template.common.ErrorCode;
import com.coding.template.common.Result;
import com.coding.template.exception.BusinessException;
import com.coding.template.model.entity.User;
import com.coding.template.model.request.UserLoginRequest;
import com.coding.template.model.request.UserQueryRequest;
import com.coding.template.model.request.UserRegisterRequest;
import com.coding.template.model.response.UserVo;
import com.coding.template.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 判断参数是否有值
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户请求体为空");
        }

        // 获取表单数据，并进行校验
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 登录逻辑
        UserVo user = userService.userLogin(userAccount, password, request);
        log.info("user => {}", JSON.toJSON(user));
        if (user == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
        return Result.ok(user);
    }

    @GetMapping("/get/login")
    public Result<UserVo> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return Result.ok(userService.getLoginUserVo(user));
    }

    /**
     * 注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Result<Long> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户请求体为空");
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkCode = userRegisterRequest.getCheckCode();
        if (StringUtils.isAnyBlank(userAccount, password, checkCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        long count = userService.userRegister(userAccount, password, checkCode);
        if (count == 0) {
            throw new BusinessException(ErrorCode.REGISTER_FAILED);
        }
        return Result.ok(count);
    }

    /**
     * 分页条件查询
     *
     * @param userQueryRequest
     * @return
     */
    @PostMapping("/page/list")
    public Result<Page<User>> fetchPage(@RequestBody UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户请求体为空");
        }
        String userAccount = userQueryRequest.getUserAccount();
        String username = userQueryRequest.getUsername();
        long current = userQueryRequest.getCurrent();
        long limit = userQueryRequest.getLimit();
        Page<User> page = userService.page(new Page<>(current, limit), getQueryWrapper(userQueryRequest));
        return Result.ok(page);
    }

    /**
     * 获取查询条件
     *
     * @param userQueryRequest
     * @return
     */
    private QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        String userAccount = userQueryRequest.getUserAccount();
        String username = userQueryRequest.getUsername();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isBlank(userAccount), "userAccount", userAccount);
        queryWrapper.like(StringUtils.isBlank(username), "username", username);

        return queryWrapper;
    }
}
