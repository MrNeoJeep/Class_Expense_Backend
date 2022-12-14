package com.future.controller;

import com.future.entity.Class;
import com.future.service.ClassService;
import com.future.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ClassControllerTest {

    @Autowired
    ClassService classService;
    @Test
    void addClass() {
        Class cls = new Class();
        cls.setId(UUIDUtil.getUUID32());
        cls.setClassName("计算机2012");
        System.out.println(cls.toString());
        classService.save(cls);

    }
}