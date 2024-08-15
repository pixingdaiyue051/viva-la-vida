package com.tequeno.constants;

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

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
