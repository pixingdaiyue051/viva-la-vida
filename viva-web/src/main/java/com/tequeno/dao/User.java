package com.tequeno.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User {

    private Long id;

    private String name;

    private String gender;

    private String avatar;
}
