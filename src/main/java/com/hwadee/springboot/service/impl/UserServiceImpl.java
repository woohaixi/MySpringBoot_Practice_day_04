package com.hwadee.springboot.service.impl;

import com.hwadee.springboot.entity.User;
import com.hwadee.springboot.entity.UserEntity;
import com.hwadee.springboot.mapper.MenuMapper;
import com.hwadee.springboot.mapper.UserMapper;
import com.hwadee.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }


    @Override
    public UserEntity get(int id) {
        return userMapper.get(id);
    }

    @Override
    public UserEntity getUserByAccountName(String accountName){
        return userMapper.getUserByAccountName(accountName);
    }

    @Override
    public List<UserEntity> list(Map<String, Object> map) {
        return userMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userMapper.count(map);
    }

    @Override
    public int save(UserEntity user) {
        //新增用户
        int save = userMapper.save(user);
        //新增用户角色关系
        if (save > 0) {
            userMapper.insertUserRole(user);
        }
        return save;
    }

    @Override
    public int update(UserEntity user) {
        return userMapper.update(user);
    }

    @Override
    public int remove(int userId) {
        return userMapper.remove(userId);
    }

    @Override
    public UserEntity getUserByLoginInfo(Map<String, Object> map) {
        return userMapper.getUserByLoginInfo(map);
    }
}
