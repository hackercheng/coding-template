-- 创建库
create database if not exists code-template;

-- 切换库
use code-template;

-- 用户表
create table if not exists user
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `create_time` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `update_time` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `is_deleted` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)',
    `username` varchar(256) not null comment '用户名',
    `userAccount` varchar(256) not null comment '账户名',
    `password` varchar(256) not null comment '密码',
    `userAvatar` varchar(256) not null comment '用户头像',
    `phone` varchar(256) not null comment '手机号',
    `email` varchar(256) not null comment '邮箱',
    `userRole` tinyint default 0 not null comment '用户角色(0-管理员，1-用户)'
) comment '用户表';