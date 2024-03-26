package com.tianll.blog.model.mapper;

import com.tianll.blog.model.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tianll");
        user.setPassword("123456");
        user.setEmail("<EMAIL>");
        user.setCreated(new java.util.Date());
        user.setValid(1);
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    @Test
    public void insertSelective() {
        User user = new User();
        user.setUsername("tianll");
        user.setPassword("<PASSWORD>");
//        user.setEmail("<EMAIL>");
        user.setCreated(new java.util.Date());
        user.setValid(1);
        int result = userMapper.insertSelective(user);
        System.out.println(result);
    }

    @Test
    public void selectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }

    @Test
    public void updateByPrimaryKeySelective() {
        User user = new User();
        user.setId(1);
//        user.setUsername("tianll");
//        user.setPassword("<PASSWORD>");
//        user.setEmail("<EMAIL>");
//        user.setCreated(new java.util.Date());
        user.setValid(0);
        int result = userMapper.updateByPrimaryKeySelective(user);
        System.out.println(result);
    }

    @Test
    public void updateByPrimaryKey() {
        User user = new User();
        user.setId(2);
        user.setUsername("tianll");
        user.setPassword("<PASSWORD>");
        user.setEmail("<EMAIL>");
        user.setCreated(new java.util.Date());
        user.setValid(0);
        int result = userMapper.updateByPrimaryKey(user);
        System.out.println(result);
    }
}