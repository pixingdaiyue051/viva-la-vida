package com.tequeno.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class HtJmsRocketModel {

    private String code;

    private Object data;

    private String tag;

    private String key;

    private long timestamp;

    private Long delay;
}