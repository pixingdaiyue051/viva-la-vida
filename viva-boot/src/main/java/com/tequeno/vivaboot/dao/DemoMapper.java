package com.tequeno.vivaboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tequeno.dto.demo.DemoDetailDto;
import com.tequeno.dto.demo.DemoQueryDto;
import com.tequeno.vivaboot.entity.Demo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

    @Select({
        "<script>",
            "select count(1) from demo d ",
            "<where><if test = 'name != null'>d.name = #{name} </if></where>",
        "</script>"
    })
    long selectDemoCount(DemoQueryDto dto);

    @Select({
        "<script>",
            "select d.id,d.name from demo d ",
            "<where><if test = 'name != null'>d.name = #{name} </if></where>",
            "limit #{offset},#{pageSize}",
        "</script>"
    })
    List<DemoDetailDto> selectDemoPage(DemoQueryDto dto);

}
