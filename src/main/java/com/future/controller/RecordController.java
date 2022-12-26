package com.future.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.future.common.dto.RecordDto;
import com.future.common.lang.Result;
import com.future.config.Constants;
import com.future.entity.Class;
import com.future.entity.Record;
import com.future.entity.User;
import com.future.service.ClassService;
import com.future.service.RecordService;
import com.future.service.UserService;
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

    @Autowired
    UserService userService;

    @Autowired
    ClassService classService;

    //添加记录
    @RequestMapping("/add")
    public Result addRecord(@RequestBody RecordDto recordDto) {
        Record record = new Record();
        record.setId(recordDto.getId());
        record.setRecordName(recordDto.getRecordName());
        record.setRecordMoney(recordDto.getRecordMoney());
        LocalDateTime time = LocalDateTimeUtil.StrToLoaclDateTime(recordDto.getRecordDate());
        record.setRecordDate(time);
        record.setPhotos(recordDto.getPhotos());
        record.setReceipt(recordDto.getReceipt());
        String checkName = recordDto.getCheckId();
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getUsername,checkName);
        User user = userService.getOne(queryWrapper2);
        record.setCheckId(user.getId());
//        record.setCheckId(recordDto.getCheckId());
        record.setState(0);

        //查询record_name是否重复,排除已解决的问题
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Record::getRecordName,recordDto.getRecordName());
        Record one = recordService.getOne(queryWrapper);
        if(one != null && one.getState() == 0) {
            return Result.fail("记录名重复");
        }

        //查询班费,修改班费
        LambdaQueryWrapper<Class> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(Class::getClassName, Constants.CLASSNAME);
        Class aClass = classService.getOne(queryWrapper3);
        if(aClass.getExpense() - record.getRecordMoney() < 0){
            return Result.fail("添加失败，班费不足");
        }
        aClass.setExpense(aClass.getExpense() - record.getRecordMoney());
        classService.update(aClass,queryWrapper3);
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


        record.setRecordMoney(recordDto.getRecordMoneyNew());
        LocalDateTime time = LocalDateTimeUtil.StrToLoaclDateTime(recordDto.getRecordDate());
        record.setRecordDate(time);
        record.setPhotos(recordDto.getPhotos());
        record.setReceipt(recordDto.getReceipt());

        //检查checkname 是否存在
        String checkName = recordDto.getCheckId();
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getUsername,checkName);
        User user = userService.getOne(queryWrapper2);
        record.setCheckId(user.getId());
        //record.setCheckId(recordDto.getCheckId());
        record.setState(0);

        //查询班费,修改班费
        LambdaQueryWrapper<Class> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(Class::getClassName, Constants.CLASSNAME);
        Class aClass = classService.getOne(queryWrapper3);
        Double gap = recordDto.getRecordMoneyNew() - recordDto.getRecordMoney();
        if(aClass.getExpense() - gap < 0){
            return Result.fail("更新失败，班费不足");
        }
        aClass.setExpense(aClass.getExpense() - gap);
        classService.update(aClass,queryWrapper3);

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
