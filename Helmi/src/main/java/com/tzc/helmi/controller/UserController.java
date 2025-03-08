package com.tzc.helmi.controller;

import com.tzc.helmi.entity.User;
import com.tzc.helmi.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("getAllUser")
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }
}