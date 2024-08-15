package com.tequeno.utils;

import com.tequeno.constants.HtPropertyConstant;
import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.word.RandomWordFactory;

import java.awt.*;

/**
 * @Desription:
 * @Author: hexk
 */
public class HtCaptchaUtil {

    /**
     * 生成图片验证码
     */
    public static Captcha captchaPic() {
        return HtCaptchaHolder.captchaPic();
    }

    /**
     * 生成文字验证码
     */
    public static String captchaOtp() {
        return HtCaptchaHolder.captchaOtp();
    }

    private static class HtCaptchaHolder {

        private static ConfigurableCaptchaService ccs;
        private static ColorFactory cf;
        private static RandomFontFactory ff;
        private static RandomWordFactory rwf;
        private static TextRenderer tr;
        /* 验证码外观样式 */
        //	private static WobbleRippleFilterFactory wrff;
        //	private static MarbleRippleFilterFactory mrff;
        //	private static DoubleRippleFilterFactory drff;
        private static CurvesRippleFilterFactory crff;

        /**
         * 初始化参数
         */
        static {
            ccs = new ConfigurableCaptchaService();
            cf = new SingleColorFactory(new Color(25, 60, 170));
            ff = new RandomFontFactory();
            rwf = new RandomWordFactory();
            tr = new BestFitTextRenderer();

            /* 样式 */
            //		wrff = new WobbleRippleFilterFactory();// 摆波纹
            //		mrff = new MarbleRippleFilterFactory(); // 大理石波纹
            //		drff = new DoubleRippleFilterFactory();  //双波纹
            crff = new CurvesRippleFilterFactory(new SingleColorFactory(new Color(153, 204, 255))); //干扰线波纹

            rwf.setCharacters(HtPropertyConstant.NUMBER_STR);
            rwf.setMaxLength(HtPropertyConstant.OTP_LENGTH);
            rwf.setMinLength(HtPropertyConstant.OTP_LENGTH);
            ff.setRandomStyle(false);
            ff.setMaxSize(30);
            ff.setMinSize(30);
            ccs.setTextRenderer(tr);
            ccs.setFontFactory(ff);
            ccs.setWordFactory(rwf);
            ccs.setColorFactory(cf);
            ccs.setWidth(200);
            ccs.setHeight(40);
            ccs.setFilterFactory(crff);
        }

        private static Captcha captchaPic() {
            return ccs.getCaptcha();
        }

        private static String captchaOtp() {
            return rwf.getNextWord();
        }
    }
}
