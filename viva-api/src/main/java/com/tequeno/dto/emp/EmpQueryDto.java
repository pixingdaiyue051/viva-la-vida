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

    private String no;

    private String noLike;

    private String name;

    private String nameLike;

    private String status;

    private String statusGt;

    private String statusLt;

}
