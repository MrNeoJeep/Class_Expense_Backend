package com.future.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.future.common.dto.LoginDto;
import com.future.common.dto.RegisterDto;
import com.future.common.lang.Result;
import com.future.entity.Class;
import com.future.entity.Record;
import com.future.entity.User;
import com.future.service.ClassService;
import com.future.service.UserService;
import com.future.util.JwtUtils;
import com.future.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MrNeo
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ClassService classService;

    @Autowired
    JwtUtils jwtUtils;
    //注册
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDto registerDto) {

        User user = new User();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,registerDto.getUsername());
        User user1 = userService.getOne(queryWrapper);
        if(user1 != null){
            return Result.fail("用户已存在");
        }
        user.setUsername(registerDto.getUsername());

        //设置uuid
        user.setId(UUIDUtil.getUUID32());
        //设置密码
        String password = registerDto.getPassword();
        password = SecureUtil.md5(password);
        user.setPassword(password);
        //设置班级
        LambdaQueryWrapper<Class> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Class::getClassName,"计算机2012");
        Class aClass = classService.getOne(queryWrapper1);
        user.setClassId(aClass.getId());


        //设置角色
        user.setRole(3);
        userService.save(user);
        //班级人数加1
        aClass.setClassNum(aClass.getClassNum() + 1);
        classService.update(aClass,queryWrapper1);

        return Result.success(user);
    }

    @CrossOrigin
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        if(user == null) {
            return Result.fail("用户不存在");
        }

        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }

        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        Class aClass = classService.getById(user.getClassId());

        return Result.success(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("classname",aClass.getClassName())
                .put("role",user.getRole())
                .map()
        );
    }


    //查询所有用户信息
    @RequestMapping("/findAll")
    public Result findAll() {
        List<User> list = userService.list();
        //删除密码等隐私数据
        for(int i = 0;i < list.size();i++) {
            list.get(i).setPassword("");
        }
        return Result.success(list);
    }

    //根据用户名查用户
    @RequestMapping("/find")
    public Result findByName(@RequestParam String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        User user = userService.getOne(queryWrapper);
        //删除密码等隐私数据
        user.setPassword("");
        return Result.success(user);
    }

    //根据用户名查用户
    @RequestMapping("/findById")
    public Result findById(@RequestParam String id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        User user = userService.getOne(queryWrapper);
        //删除密码等隐私数据
        user.setPassword("");
        return Result.success(user);
    }

}
