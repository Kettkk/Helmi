package com.tzc.helmi.controller;

import com.tzc.helmi.entity.Admin;
import com.tzc.helmi.mapper.AdminMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminMapper adminMapper;

    @RequestMapping("getAllAdmin")
    public List<Admin> getAllAdmin() {
        return adminMapper.selectAll();
    }

    @PostMapping("login")
    public String login(@RequestBody Admin admin) {
        Admin login = adminMapper.login(admin.getUsername(), admin.getPassword());
        if (login != null) {
            return "success";
        } else {
            return "fail";
        }
    }
}
