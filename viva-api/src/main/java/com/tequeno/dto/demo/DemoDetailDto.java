package com.tequeno.dto.demo;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DemoDetailDto {

    private Long id;

    private String name;
}
