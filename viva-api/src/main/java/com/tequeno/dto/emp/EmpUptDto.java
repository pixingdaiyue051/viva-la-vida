package com.tequeno.dto.emp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EmpUptDto {

    private Long id;

    private String no;

    private String name;

}
