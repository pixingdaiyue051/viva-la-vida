package com.tequeno.dto.hero;

import com.tequeno.constants.HtCommonQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class HeroQueryDto extends HtCommonQuery {

    private String name;

    private String nameLike;

    private String nickName;

    private String nickNameLike;

    private Integer type;

    private Integer rankGt;

    private Integer rankLt;

}
