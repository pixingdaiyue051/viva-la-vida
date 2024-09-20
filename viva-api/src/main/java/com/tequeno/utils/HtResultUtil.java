package com.tequeno.utils;

import com.tequeno.dto.HtResultModel;
import com.tequeno.enums.HtCommonErrorEnum;
import com.tequeno.enums.HtErrorInterface;

public class HtResultUtil {

    public static HtResultModel success(HtErrorInterface errorImpl, Object data) {
        return success(errorImpl).setData(data);
    }

    public static HtResultModel success(HtErrorInterface errorImpl) {
        return new HtResultModel()
                .setSuccess(true)
                .setCode(errorImpl.getCode())
                .setMsg(errorImpl.getMsg());
    }

    public static HtResultModel success(Object data) {
        return success(HtCommonErrorEnum.SUCCESS, data);
    }

    public static HtResultModel success() {
        return success(HtCommonErrorEnum.SUCCESS);
    }

    public static HtResultModel fail(HtErrorInterface errorImpl) {
        return new HtResultModel()
                .setSuccess(false)
                .setCode(errorImpl.getCode())
                .setMsg(errorImpl.getMsg());
    }

    public static HtResultModel fail(String errorMsg) {
        return fail().setMsg(errorMsg);
    }

    public static HtResultModel fail() {
        return fail(HtCommonErrorEnum.FAIL);
    }
}
