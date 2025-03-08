package com.tzc.helmi.controller;


import com.tzc.helmi.entity.Statu;
import com.tzc.helmi.mapper.StatuMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statu")
public class StatuController {

    @Resource
    private StatuMapper statuMapper;

    @RequestMapping("getAllStatu")
    public List<Statu> getAllStatu() {
        return statuMapper.selectAll();
    }
}
