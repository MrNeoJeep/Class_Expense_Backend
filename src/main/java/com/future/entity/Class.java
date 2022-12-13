package com.future.entity;

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
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 班级名
     */
    private String className;

    /**
     * 班级人数
     */
    private Integer classNum;

    /**
     * 记账人ID
     */
    private String bookkeeperId;

    /**
     * 班费，保留两位小数
     */
    private Double expense;


}
