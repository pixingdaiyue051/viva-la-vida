package com.tequeno.constants;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class HtCommonPage<T> {

    private long total;

    private List<T> records;

    public HtCommonPage() {
        this.total = 0;
        this.records = List.of();
    }
}
