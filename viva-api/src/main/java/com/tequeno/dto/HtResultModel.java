package com.tequeno.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class HtResultModel {

    private boolean success;

    private String code;

    private String msg;

    private Object data;
}
