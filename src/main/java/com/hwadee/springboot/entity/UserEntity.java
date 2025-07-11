package com.hwadee.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {
    // 用户id
    private Integer userId;
    // 姓名
    private String name;
    // 手机号
    private String phone;
    // 创建时间
    private Date createDate;
    // 账号名
    private String account;
    // 账号密码
    private String password;
}
