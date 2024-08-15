package com.tequeno.vivabootalpha.config;

import com.tequeno.dto.HtResultModel;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.enums.HtCommonErrorEnum;
import com.tequeno.dto.HtCommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.tequeno.vivabootalpha.controller")
public class ExceptionAspect {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    @ExceptionHandler
    public HtResultModel error(Exception e) {
        logger.error("内部异常:", e);
        if (e instanceof HtCommonException) {
            return HtResultUtil.fail(((HtCommonException) e).getErrorImpl());
        }
        return HtResultUtil.fail(HtCommonErrorEnum.SYSTEM_ERROR);
    }
}
