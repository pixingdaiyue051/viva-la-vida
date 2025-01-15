package com.tequeno.constants;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class HtCommonPage<T> {

    /**
     * 总数据量
     */
    private long total;

    /**
     * 当前页数据量
     */
    private int size;

    private List<T> records;

    public HtCommonPage() {
        this.total = 0;
        this.size = 0;
        this.records = List.of();
    }
}
