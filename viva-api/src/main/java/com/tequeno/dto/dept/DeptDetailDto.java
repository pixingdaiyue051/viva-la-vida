package com.tequeno.dto.dept;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class DeptDetailDto {

    private Long id;

    private String no;

    private String name;

    private Integer status;

    private LocalDateTime createTime;
}