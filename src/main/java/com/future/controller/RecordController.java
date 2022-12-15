package com.future.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.future.common.lang.Result;
import com.future.entity.Record;
import com.future.service.RecordService;
import com.future.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MrNeo
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    //添加记录
    @RequestMapping("/add")
    public Result addRecord(@RequestBody Record record) {
        record.setId(UUIDUtil.getUUID32());
        recordService.save(record);
        return Result.success("添加成功");
    }

    //查询所有record记录
    @RequestMapping("/findAll")
    public Result findAll() {
        List<Record> records = recordService.list();
        return Result.success(records);
    }

    //根据record名查询记录
    @RequestMapping("/find")
    public Result findByName(@RequestParam String recordName) {
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Record::getRecordName,recordName);
        Record record = recordService.getOne(queryWrapper);

        return Result.success(record);
    }

}
