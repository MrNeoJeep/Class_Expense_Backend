package com.future.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.future.common.lang.Result;
import com.future.entity.Class;
import com.future.entity.Record;
import com.future.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MrNeo
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    ClassService classService;


    //增加班级
    @RequestMapping("/addClass")
    public Result addClass(@RequestBody Class cls) {
        classService.save(cls);
        return Result.success(cls);
    }

    //获取班费
    @RequestMapping("/getExpense")
    public Result getExpense(@RequestParam String className) {
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getClassName,className);
        Class one = classService.getOne(queryWrapper);
        return Result.success(one.getExpense());
    }
    @RequestMapping("/updateExpense")
    public Result updateExpense(@RequestParam String className,@RequestParam double expense){
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_name",className);
        Class one = classService.getOne(new QueryWrapper<Class>().eq("class_name", className));
        one.setExpense(expense);
        classService.update(one,queryWrapper);
        return Result.success("更新成功");
    }



}
