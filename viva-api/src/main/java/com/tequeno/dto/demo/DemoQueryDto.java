package com.tequeno.dto.demo;

import com.tequeno.constants.HtCommonQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class DemoQueryDto extends HtCommonQuery {

    private String name;

    private String nameLike;

    private String desc;

    private String descLike;

}
