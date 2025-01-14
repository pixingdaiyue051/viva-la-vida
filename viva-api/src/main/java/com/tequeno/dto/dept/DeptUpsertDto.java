package com.tequeno.dto.dept;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DeptUpsertDto {

    private Long id;

    private String no;

    private String name;

    private Integer status;

}
