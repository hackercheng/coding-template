package com.coding.template.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coding.template.common.DeleteRequest;
import com.coding.template.common.ErrorCode;
import com.coding.template.common.Result;
import com.coding.template.exception.BusinessException;
import com.coding.template.exception.ThrowUtils;
import com.coding.template.model.entity.User;
import com.coding.template.model.request.*;
import com.coding.template.model.response.UserVo;
import com.coding.template.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

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
        log.info("params =>",JSON.toJSON(userLoginRequest));
        // 获取表单数据，并进行校验
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getUserPassword();
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

    /**
     * 获取登录用户
     *
     * @param request
     * @return
     */
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
        log.info("params =>",JSON.toJSON(userRegisterRequest));
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getUserPassword();
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

    // crud.start

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
        log.info("param => {}",JSON.toJSON(userQueryRequest));
        long current = userQueryRequest.getCurrent();
        long limit = userQueryRequest.getLimit();
        Page<User> page = userService.page(new Page<>(current, limit), getQueryWrapper(userQueryRequest));
        return Result.ok(page);
    }

//    /**
//     * 新增用户
//     * @param userAddRequest
//     * @return
//     */
//    @PostMapping("/add")
//    public Result<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
//        if (userAddRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        log.info("param => {}",JSON.toJSON(userAddRequest));
//        User user = new User();
//        BeanUtils.copyProperties(userAddRequest, user);
//        log.info("user => {}",JSON.toJSON(user));
//        boolean result = userService.save(user);
//        ThrowUtils.throwIf(!result, ErrorCode.PARAMS_ERROR);
//        return Result.ok(user.getId());
//    }

    /**
     * 删除用户
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    public Result<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        log.info("id:",deleteRequest.getId());
        boolean remove = userService.removeById(deleteRequest.getId());
        return Result.ok(remove);
    }

    @PostMapping("/update")
    public Result<Boolean> updateUser(@RequestBody UserUpdateRquest userUpdateRquest) {
        if (userUpdateRquest == null || userUpdateRquest.getId()<=0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        log.info("params => {}",JSON.toJSON(userUpdateRquest));
        User user = new User();
        BeanUtils.copyProperties(userUpdateRquest,user);
        log.info("user json => {}"+JSON.toJSON(user));
        boolean update = userService.updateById(user);
        ThrowUtils.throwIf(!update,ErrorCode.PARAMS_ERROR);
        return Result.ok(true);
    }
    // crud.end

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
        String username = userQueryRequest.getUserName();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isBlank(userAccount), "userAccount", userAccount);
        queryWrapper.like(StringUtils.isBlank(username), "username", username);

        return queryWrapper;
    }
}
