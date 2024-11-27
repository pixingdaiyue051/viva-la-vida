package com.tequeno.dto.emp;

import com.tequeno.constants.HtCommonQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.File;

@Getter
@Setter
@Accessors(chain = true)
public class EmpQueryDto extends HtCommonQuery {

    private String noLike;

    private String nameLike;

    private String statusEq;

}
