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
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 记录id
     */
    private String recordId;

    /**
     * 回复人id
     */
    private String userId;

    /**
     * 回复内容
     */
    private String replyContent;


}
