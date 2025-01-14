package com.tequeno.dto.emp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EmpUpsertDto {

    private Long id;

    private String no;

    private String name;

    private Integer status;

    private Long deptId;

}