package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.entity.MenuEntity;
import com.hwadee.springboot.mapper.MenuMapper;
import com.hwadee.springboot.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Override
    public MenuEntity get(int menuId) {
        return menuMapper.get(menuId);
    }

    @Override
    public List<MenuEntity> list(Map<String, Object> map) {
        return menuMapper.list(map);
    }

    @Override
    public List<MenuEntity> listByRoleId(int[] roleIds) {
        return menuMapper.listByRoleId(roleIds);
    }

    @Override
    public List<MenuEntity> listByUserId(int userId) {
        return menuMapper.listByUserId(userId);
    }

    @Override
    public int save(MenuEntity menu) {
        return menuMapper.save(menu);
    }

    @Override
    public int update(MenuEntity menu) {
        return menuMapper.update(menu);
    }

    @Override
    public int remove(int menuId) {
        return menuMapper.remove(menuId);
    }

}
