package com.tequeno.constants;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class HtCommonQuery {

    private long pageNum;

    private long pageSize;

    public HtCommonQuery() {
        pageNum = 1;
        pageSize = 10;
    }

    public long getOffset() {
        return (pageNum - 1) * pageSize;
    }
}