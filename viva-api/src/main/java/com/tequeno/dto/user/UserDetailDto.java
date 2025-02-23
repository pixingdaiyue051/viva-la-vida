package com.tequeno.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class UserDetailDto {

    private Long id;

    private String name;

    private String gender;

    private String avatar;
}
