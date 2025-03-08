package com.tzc.helmi.mapper;

import com.tzc.helmi.entity.Statu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StatuMapper {

    @Select("SELECT * FROM device_statu")
    List<Statu> selectAll();
}
