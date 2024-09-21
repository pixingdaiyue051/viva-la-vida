package com.tequeno.dto;

import com.tequeno.enums.HtErrorInterface;
import lombok.Getter;

@Getter
public class HtCommonException extends RuntimeException {

    private final HtErrorInterface errorImpl;

    public HtCommonException(HtErrorInterface errorImpl) {
        super();
        this.errorImpl = errorImpl;
    }
}
