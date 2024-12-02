package com.tequeno.vivaboot.converter;

import com.tequeno.dto.dept.DeptDetailDto;
import com.tequeno.vivaboot.entity.Dept;
import org.mapstruct.Mapper;

@Mapper
public interface DeptConverter {

    DeptDetailDto entity2Detail(Dept entity);
}
