package com.tequeno.vivaboot.aspect;

import com.tequeno.dto.HtCommonException;
import com.tequeno.dto.HtResultModel;
import com.tequeno.enums.HtCommonErrorEnum;
import com.tequeno.utils.HtResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.tequeno.vivaboot.controller")
@Slf4j
public class ExceptionAspect {

    @ExceptionHandler
    public HtResultModel run(Exception e) {
        log.error("内部异常:", e);
        if (e instanceof HtCommonException) {
            return HtResultUtil.fail(((HtCommonException) e).getErrorImpl());
        }
        return HtResultUtil.fail(HtCommonErrorEnum.SYSTEM_ERROR);
    }
}
