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
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 归属于哪一条记录
     */
    private String recordId;

    /**
     * 提出质疑的人的id
     */
    private String userId;

    /**
     * 提出时间
     */
    private LocalDateTime questionTime;

    /**
     * 质疑内容
     */
    private String content;

    /**
     * 确认人数
     */
    private Integer confirmNum;

    /**
     * 质疑人是否确认
     */
    private Integer confirmOwn;

    /**
     * 回复的id
     */
    private String replyId;

    /**
     * 状态，0：未解决，1：解决了当前质疑
     */
    private Integer state;


}
