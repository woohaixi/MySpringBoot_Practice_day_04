package com.hwadee.springboot.mapper;


import com.hwadee.springboot.entity.User;
import com.hwadee.springboot.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 针对表【user】的数据库操作Mapper
 */
public interface UserMapper {

    /**
     * 查询所有用户
     * @return
     */
    List<User> selectAll();

    UserEntity get(int userId);

    UserEntity getUserByAccountName(String accountName);

    List<UserEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UserEntity user);

    int update(UserEntity user);

    int remove(int userId);

    int batchRemove(Integer[] userIds);

    UserEntity getUserByLoginInfo(Map<String, Object> map);


    /**
     * 新增用户角色关系
     * @param user
     */
    int insertUserRole(UserEntity user);
}
