# 后端模板使用说明书

## 技术栈

- SSM
- Springboot
- MySQL
- Redis

## 安装运行应用步骤

获取模板代码

> git clone git@e.coding.net:hwork/coding-template/coding-template.git

创建数据库

> 运行模板中sql文件夹中的create_sql.sql文件，创建模板需要使用的数据库

修改数据库配置

```application.yml
spring:
  database:
    url: jdbc:mysql://localhost:3306/code_db
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: xxx # 这里需要修改为你自己的数据库用户名
    password: xxxxxxxx # 这里需要修改为你自己的数据库密码
```

**到此项目的基本配置已经完成，直接运行即可**

ps：直接运行MainApplication即可运行SpringBoot项目。

## Spring Session 分布式登录

