package com.hwadee.springboot.service;


import com.hwadee.springboot.entity.MenuEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MenuService {
	MenuEntity get(int menuId);

	List<MenuEntity> list(Map<String, Object> map);

	List<MenuEntity> listByRoleId(int[] roleIds);

	List<MenuEntity> listByUserId(int userId);

	int save(MenuEntity menu);

	int update(MenuEntity menu);

	int remove(int menuId);

}
