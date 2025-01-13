package com.tequeno.vivaboot.converter;

import com.tequeno.dto.demo.DemoCrtDto;
import com.tequeno.dto.demo.DemoDetailDto;
import com.tequeno.dto.demo.DemoUptDto;
import com.tequeno.vivaboot.entity.Demo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DemoConverter {

    DemoConverter INSTANCE = Mappers.getMapper(DemoConverter.class);

    Demo crt2Entity(DemoCrtDto dto);

    Demo upt2Entity(DemoUptDto dto);

    DemoDetailDto entity2Detail(Demo entity);
}
