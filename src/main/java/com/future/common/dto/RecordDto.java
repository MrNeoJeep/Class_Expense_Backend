package com.future.common.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RecordDto {
    /**
     * 记录名
     */
    private String recordName;

    /**
     * 购买日期
     */
    private String recordDate;

    /**
     * 金额
     */
    private Double recordMoney;

    /**
     * 实物照片存储路径
     */
    private String photos;

    /**
     * 小票路径
     */
    private String receipt;

    /**
     * 验收人ID
     */
    private String checkId;
}
