package com.tequeno.vivaboot.converter;

import com.tequeno.dto.dept.DeptDetailDto;
import com.tequeno.vivaboot.entity.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeptConverter {

    DeptConverter INSTANCE = Mappers.getMapper(DeptConverter.class);

    DeptDetailDto entity2Detail(Dept entity);
}
