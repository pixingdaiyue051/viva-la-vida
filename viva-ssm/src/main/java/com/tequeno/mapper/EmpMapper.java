package com.tequeno.mapper;

import com.tequeno.dto.emp.EmpDetailDto;
import org.apache.ibatis.annotations.Select;

public interface EmpMapper {

    @Select("select * from emp where id = #{id}")
    EmpDetailDto selectById(Long id);
}
