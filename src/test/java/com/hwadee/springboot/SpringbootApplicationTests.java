package com.hwadee.springboot;

import com.hwadee.springboot.controller.HelloController;
import com.hwadee.springboot.controller.UserController;
import com.hwadee.springboot.entity.User;
import com.hwadee.springboot.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private HelloController helloController;

    @Autowired
    private UserMapper userMapper;


    @Test
    void contextLoads() {
        String hello = helloController.hello();
        System.out.println(hello);
    }


    @Test
    void testSelectAll(){
        List<User> users = userMapper.selectAll();
        users.forEach(System.out::println);
    }





}
