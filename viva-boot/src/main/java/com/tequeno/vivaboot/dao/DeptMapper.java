package com.tequeno.vivaboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tequeno.vivaboot.entity.Dept;
import com.tequeno.vivaboot.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

}
