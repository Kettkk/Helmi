package com.tzc.helmi.controller;

import com.tzc.helmi.entity.Device;
import com.tzc.helmi.mapper.DeviceMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Resource
    private DeviceMapper deviceMapper;

    @RequestMapping("getAllDevice")
    public List<Device> getAllDevice() {
        return deviceMapper.selectAll();
    }
}
