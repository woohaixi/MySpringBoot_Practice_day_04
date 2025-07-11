package com.hwadee.springboot.mapper;


import com.hwadee.springboot.entity.MenuEntity;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    MenuEntity get(int menuId);

    List<MenuEntity> list(Map<String,Object> map);

    List<MenuEntity> listByRoleId(int[] roleIds);

    List<MenuEntity> listByUserId(int userId);

    int save(MenuEntity menu);

    int update(MenuEntity menu);

    int remove(int menuId);

}
