package com.tequeno.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Jdbc {

    private Mysql mysql;

    private Sqlite sqlite;
}
