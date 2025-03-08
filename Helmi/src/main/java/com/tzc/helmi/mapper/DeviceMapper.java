package com.tzc.helmi.mapper;

import com.tzc.helmi.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeviceMapper {

    @Select("SELECT * FROM device")
    List<Device> selectAll();
}
