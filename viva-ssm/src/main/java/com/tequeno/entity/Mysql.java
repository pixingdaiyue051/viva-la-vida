package com.tequeno.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Mysql {

    private String driver;

    private String url;

    private String user;

    private String password;
}
