package com.future.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.future.common.dto.RecordDto;
import com.future.common.lang.Result;
import com.future.entity.Record;
import com.future.service.RecordService;
import com.future.util.LocalDateTimeUtil;
import com.future.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public Result addRecord(@RequestBody RecordDto recordDto) {
        Record record = new Record();
        record.setId(UUIDUtil.getUUID32());
        record.setRecordName(recordDto.getRecordName());
        record.setRecordMoney(recordDto.getRecordMoney());
        LocalDateTime time = LocalDateTimeUtil.StrToLoaclDateTime(recordDto.getRecordDate());
        record.setRecordDate(time);
        record.setPhotos(recordDto.getPhotos());
        record.setReceipt(recordDto.getReceipt());
        record.setCheckId(recordDto.getCheckId());
        record.setState(0);

        //查询record_name是否重复,排除已解决的问题
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Record::getRecordName,recordDto.getRecordName());
        Record one = recordService.getOne(queryWrapper);
        if(one != null && one.getState() == 0) {
            return Result.fail("记录名重复");
        }

        recordService.save(record);
        return Result.success("添加成功");
    }

    //更新,记录名不允许修改
    @PostMapping("/update")
    public Result update(@RequestBody RecordDto recordDto) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("record_name",recordDto.getRecordName());
        Record record = recordService.getOne(new QueryWrapper<Record>().eq("record_name",recordDto.getRecordName()));
        if(record == null) {
            return Result.fail("未知错误");
        }
//        List<Record> list = recordService.list();
//        int cnt = 0;
//        for (Record record1 : list) {
//            if(record1.getRecordName().equals(recordDto.getRecordName())){
//                cnt++;
//            }
//        }
//        if(cnt > 1) {
//            return Result.fail("记录名重复");
//        }
//        record.setRecordName(recordDto.getRecordName());
        record.setRecordMoney(recordDto.getRecordMoney());
        LocalDateTime time = LocalDateTimeUtil.StrToLoaclDateTime(recordDto.getRecordDate());
        record.setRecordDate(time);
        record.setPhotos(recordDto.getPhotos());
        record.setReceipt(recordDto.getReceipt());
        record.setCheckId(recordDto.getCheckId());
        record.setState(0);
        recordService.update(record,queryWrapper);
        return Result.success("修改成功");
    }

    //修改state状态
    @RequestMapping("/changeState")
    public Result changeState(@RequestParam String recordName) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("record_name",recordName);
        Record record = recordService.getOne(new QueryWrapper<Record>().eq("record_name",recordName));
        if(record == null) {
            return Result.fail("未知错误");
        }
        if(record.getState() == 0){
            record.setState(1);
        }else {
            record.setState(0);
        }
        recordService.update(record,queryWrapper);
        return Result.success("记录状态修改成功");
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
