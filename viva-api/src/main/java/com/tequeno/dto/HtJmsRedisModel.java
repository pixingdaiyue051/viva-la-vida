package com.tequeno.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class HtJmsRedisModel {

    private String code;

    private Object data;

    private long timestamp;

    private long delay;

}