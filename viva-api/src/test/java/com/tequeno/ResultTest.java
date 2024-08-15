package com.tequeno;

import com.tequeno.dto.HtResultModel;
import com.tequeno.enums.HtCommonErrorEnum;
import com.tequeno.utils.HtResultUtil;
import org.junit.Test;

public class ResultTest {

    @Test
    public void run() {

        HtResultModel model = HtResultUtil.fail(HtCommonErrorEnum.SIGN_NOT_FOUND);

        System.out.println(model);
    }
}
