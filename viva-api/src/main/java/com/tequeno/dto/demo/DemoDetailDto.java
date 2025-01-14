package com.tequeno.dto.demo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class DemoDetailDto {

    private Long id;

    private String name;

    private String desc;
}
