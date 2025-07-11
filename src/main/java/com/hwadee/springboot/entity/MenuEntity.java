package com.hwadee.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    // 菜单主键id
    private Integer menuId;
    // 中文名称
    private String cname;
    // 英文名称
    private String ename;
    // 接口路径
    private String path;
    // 页面路径
    private String component;
    // 菜单图标
    private String icon;
    //父菜单id
    private int pid;
    //排序
    private int sort;
    //创建时间
    private Date createDate;
    //修改时间
    private Date updateDate;

}
