package com.tequeno.vivaboot.converter;

import com.tequeno.dto.emp.EmpDetailDto;
import com.tequeno.vivaboot.entity.Emp;
import org.mapstruct.Mapper;

@Mapper
public interface EmpConverter {

    EmpDetailDto entity2Detail(Emp entity);
}
