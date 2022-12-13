package com.future.shiro;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountProfile implements Serializable {
    private String id;

    /**
     * 用户名
     */
    private String username;


    /**
     * 班级id
     */
    private String classId;

    /**
     * 角色，1：超级管理员（老师），2：管理员（记账员），3：普通用户（学生）
     */
    private Integer role;
}
