package com.future.controller;


import com.future.common.lang.Result;
import com.future.entity.Class;
import com.future.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
