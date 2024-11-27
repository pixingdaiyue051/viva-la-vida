package com.tequeno.utils;

import com.tequeno.dto.HtResultModel;
import com.tequeno.enums.HtCommonErrorEnum;
import com.tequeno.enums.HtErrorInterface;

public class HtResultUtil {

    public static HtResultModel success(String msg, Object data) {
        return new HtResultModel()
                .setSuccess(true)
                .setCode(HtCommonErrorEnum.SUCCESS.getCode())
                .setMsg(msg)
                .setData(data);
    }

    public static HtResultModel success(Object data) {
        return success(HtCommonErrorEnum.SUCCESS.getMsg(), data);
    }

    public static HtResultModel success() {
        return success(true);
    }

    public static HtResultModel fail(HtErrorInterface errorImpl) {
        return new HtResultModel()
                .setSuccess(false)
                .setCode(errorImpl.getCode())
                .setMsg(errorImpl.getMsg())
                .setData(false);
    }

    public static HtResultModel fail(String errorMsg) {
        return fail(HtCommonErrorEnum.CUSTOM_ERROR).setMsg(errorMsg);
    }

    public static HtResultModel fail() {
        return fail(HtCommonErrorEnum.FAIL);
    }
}
