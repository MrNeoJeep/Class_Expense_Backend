package com.future.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author MrNeo
 * @since 2022-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 记录名
     */
    private String recordName;

    /**
     * 购买日期
     */
    private LocalDateTime recordDate;

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

    /**
     * 状态，0未解决，1解决
     */
    private Integer state;


}
