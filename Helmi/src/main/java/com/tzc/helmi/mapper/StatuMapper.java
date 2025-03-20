package com.tzc.helmi.mapper;

import com.tzc.helmi.entity.Statu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StatuMapper {

    @Select("SELECT * FROM device_statu")
    List<Statu> selectAll();

    // 更新设备状态数据
    @Update("UPDATE device_statu SET " +
            "user_id = #{user_id}, " +
            "username = #{username}, " +
            "temperature = #{temperature}, " +
            "heart_rate = #{heart_rate}, " +
            "body_pressure = #{body_pressure}, " +
            "env_temperature = #{env_temperature}, " +
            "env_humidity = #{env_humidity}, " +
            "longitude = #{longitude}, " +
            "latitude = #{latitude}, " +
            "high_pressure = #{high_pressure}, " +
            "low_pressure = #{low_pressure}, " +
            "speed = #{speed} " +
            "WHERE device_id = #{device_id}")
    int updateByDeviceId(Statu statu);

    // 查询设备是否已存在
    @Select("SELECT COUNT(*) FROM device_statu WHERE device_id = #{device_id}")
    int countByDeviceId(int device_id);

    // 插入新设备数据（如果设备不存在）
    @Insert("INSERT INTO device_statu (device_id, user_id, username, temperature, heart_rate, body_pressure, " +
            "env_temperature, env_humidity, longitude, latitude, high_pressure, low_pressure, speed, timestamp) " +
            "VALUES (#{device_id}, #{user_id}, #{username}, #{temperature}, #{heart_rate}, #{body_pressure}, " +
            "#{env_temperature}, #{env_humidity}, #{longitude}, #{latitude}, #{high_pressure}, #{low_pressure}, " +
            "#{speed})")
    int insert(Statu statu);
}