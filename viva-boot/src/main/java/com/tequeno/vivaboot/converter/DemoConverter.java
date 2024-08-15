package com.tequeno.vivaboot.converter;

import com.tequeno.dto.demo.DemoCrtDto;
import com.tequeno.dto.demo.DemoDetailDto;
import com.tequeno.dto.demo.DemoUptDto;
import com.tequeno.vivaboot.entity.Demo;
import org.mapstruct.Mapper;

@Mapper
public interface DemoConverter {

    Demo crt2Entity(DemoCrtDto dto);

    Demo upt2Entity(DemoUptDto dto);

    DemoDetailDto entity2Detail(Demo entity);
}
