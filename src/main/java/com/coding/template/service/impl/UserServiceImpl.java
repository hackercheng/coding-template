package com.coding.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coding.template.common.ErrorCode;
import com.coding.template.exception.BusinessException;
import com.coding.template.mapper.UserMapper;
import com.coding.template.model.entity.User;
import com.coding.template.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.coding.template.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Administrator
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-10-24 14:05:45
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    private static final String SALT = "coding";

    /**
     * 用户登录
     * @param userAccount
     * @param password
     * @param request
     * @return
     */
    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount,password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        if (userAccount.length()<6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户名未通过校验");
        }

        if (password.length()<8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码未通过校验");
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+ password).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userAccount",userAccount);
        queryWrapper.like("password",encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED,"当前帐号违背注册！");
        }
        request.getSession().setAttribute(USER_LOGIN_STATE,user);
        return user;
    }

    /**
     * 用户注册
     * @param userAccount
     * @param password
     * @param checkCode
     * @return
     */
    @Override
    public long userRegister(String userAccount, String password, String checkCode) {
        if (StringUtils.isAnyBlank(userAccount,password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        if (userAccount.length()<6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户名未通过校验");
        }

        if (password.length()<8||checkCode.length()<8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码未通过校验");
        }

        if (!password.equals(checkCode)) {
            throw new BusinessException(ErrorCode.REGISTER_FAILED,"密码与检查密码不一致");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("userAccount",userAccount);
        Long count = this.baseMapper.selectCount(queryWrapper);
        if (count>0) {
            throw new BusinessException(ErrorCode.REGISTER_FAILED,"当前用户已存在");
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+ password).getBytes());
        User user  = new User();
        user.setUserAccount(userAccount);
        user.setPassword(encryptPassword);

        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.REGISTER_FAILED);
        }
        return user.getId();
    }
}




