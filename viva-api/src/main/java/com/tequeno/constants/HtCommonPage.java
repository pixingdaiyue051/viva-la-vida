package com.tequeno.constants;

import java.util.List;

public class HtCommonPage<T> {

    private long total;

    private List<T> records;

    public HtCommonPage() {
        this.total = 0;
        this.records = List.of();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
