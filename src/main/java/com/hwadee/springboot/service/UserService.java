package com.hwadee.springboot.service;

import com.hwadee.springboot.entity.User;
import com.hwadee.springboot.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 查询所有用户
     * @return
     */
    List<User> selectAll();

    UserEntity get(int id);

    UserEntity getUserByAccountName(String accountName);

    List<UserEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserEntity user);

    int update(UserEntity user);

    int remove(int userId);

    UserEntity getUserByLoginInfo(Map<String, Object> map);


}
