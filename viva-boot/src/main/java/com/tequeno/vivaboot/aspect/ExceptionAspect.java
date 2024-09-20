package com.tequeno.vivaboot.aspect;

import com.tequeno.dto.HtCommonException;
import com.tequeno.dto.HtResultModel;
import com.tequeno.enums.HtCommonErrorEnum;
import com.tequeno.utils.HtResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.tequeno.vivaboot.controller")
public class ExceptionAspect {

    private final static Logger log = LoggerFactory.getLogger(ExceptionAspect.class);

    @ExceptionHandler
    public HtResultModel run(Exception e) {
        log.error("内部异常:", e);
        if (e instanceof HtCommonException) {
            return HtResultUtil.fail(((HtCommonException) e).getErrorImpl());
        }
        return HtResultUtil.fail(HtCommonErrorEnum.SYSTEM_ERROR);
    }
}
