package com.tequeno.dto;

import com.tequeno.enums.HtErrorInterface;

public class HtCommonException extends RuntimeException {

    private final HtErrorInterface errorImpl;

    public HtCommonException(HtErrorInterface errorImpl) {
        super();
        this.errorImpl = errorImpl;
    }

    public HtErrorInterface getErrorImpl() {
        return errorImpl;
    }
}
