package com.tequeno.vivaboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tequeno.vivaboot.entity.Hero;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HeroMapper extends BaseMapper<Hero> {

}
