package com.tzc.helmi.mapper;

import com.tzc.helmi.entity.Admin;
import com.tzc.helmi.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM admin")
    List<Admin> selectAll();

    //登录查找，是否有匹配的用户名和密码
    @Select("SELECT * FROM admin WHERE username = #{username} AND password = #{password}")
    Admin login(String username, String password);
}
