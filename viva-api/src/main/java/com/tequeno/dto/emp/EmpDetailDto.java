package com.tequeno.dto.emp;

import com.tequeno.dto.dept.DeptDetailDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class EmpDetailDto {

    private Long id;

    private String no;

    private String name;

    private Integer status;

    private LocalDateTime createTime;

    private DeptDetailDto dept;
}
