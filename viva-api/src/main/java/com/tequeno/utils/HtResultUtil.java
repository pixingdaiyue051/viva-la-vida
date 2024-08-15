package com.tequeno.utils;

import com.tequeno.dto.HtResultModel;
import com.tequeno.enums.HtCommonErrorEnum;
import com.tequeno.enums.HtErrorInterface;

public class HtResultUtil {

    public static HtResultModel success(HtErrorInterface errorImpl, Object data) {
        HtResultModel binder = success(errorImpl);
        binder.setData(data);
        return binder;
    }

    public static HtResultModel success(HtErrorInterface errorImpl) {
        HtResultModel binder = new HtResultModel();
        binder.setSuccess(true);
        binder.setCode(errorImpl.getCode());
        binder.setMsg(errorImpl.getMsg());
        return binder;
    }

    public static HtResultModel success(Object data) {
        return success(HtCommonErrorEnum.SUCCESS, data);
    }

    public static HtResultModel success() {
        return success(HtCommonErrorEnum.SUCCESS);
    }

    public static HtResultModel fail(HtErrorInterface errorImpl) {
        HtResultModel binder = new HtResultModel();
        binder.setSuccess(false);
        binder.setCode(errorImpl.getCode());
        binder.setMsg(errorImpl.getMsg());
        return binder;
    }

    public static HtResultModel fail(String errorMsg) {
        HtResultModel binder = fail();
        binder.setMsg(errorMsg);
        return binder;
    }

    public static HtResultModel fail() {
        return fail(HtCommonErrorEnum.FAIL);
    }
}
