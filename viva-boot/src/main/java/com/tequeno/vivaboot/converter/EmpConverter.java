package com.tequeno.vivaboot.converter;

import com.tequeno.dto.emp.EmpDetailDto;
import com.tequeno.dto.emp.EmpUpsertDto;
import com.tequeno.vivaboot.entity.Emp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmpConverter {

    EmpConverter INSTANCE = Mappers.getMapper(EmpConverter.class);

    EmpDetailDto entity2Detail(Emp entity);

    Emp upt2Entity(EmpUpsertDto dto);
}
