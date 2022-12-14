package com.future.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.future.entity.Class;
import com.future.entity.User;
import com.future.mapper.UserMapper;
import com.future.service.ClassService;
import com.future.service.UserService;
import com.future.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    ClassService classService;
    @Test
    void register() {
        User user = new User();
        user.setUsername("张三");
        user.setPassword("123456");
        //设置uuid
        user.setId(UUIDUtil.getUUID32());
        //设置密码
        String password = user.getPassword();
        password = SecureUtil.md5(password);
        user.setPassword(password);
        //设置班级
//        user.setClassId(classService.getOne(new QueryWrapper<Class>().
//                eq("className","计算机2012")).getId());
        user.setClassId("994692a9fb6a47fc93e22550fcc0ec5a");


        //设置角色
        user.setRole(3);
        System.out.println(user.toString());
        userService.save(user);
    }
}