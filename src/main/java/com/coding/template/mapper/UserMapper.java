package com.coding.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coding.template.model.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Administrator
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-10-24 14:05:45
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from User")
    List<User> list();

    @Select("select * from User")
    User list1();
}




