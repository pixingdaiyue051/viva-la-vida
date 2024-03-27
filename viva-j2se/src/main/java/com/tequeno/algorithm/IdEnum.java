package com.tequeno.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1~6   代表的是地区信息
 * 7~14  代表出生年月日
 * 15~16 代表身份证所在地辖区派出所的编号
 * 17    奇数    男性
 * 偶数    女性
 * 18    (1*7+2*9+3*10+4*5+5*8+6*4+7*2+8*1+9*6+10*3+11*7+12*9+13*10+14*5+15*8+16*4+17*2)%11
 * <p>
 * 0（1）、1（0）、2（X）、3（9）、4（8）、5（7）、6（6）、7（5）、8（4）、9（3）、10（2）
 */
public enum IdEnum {

    ONE1(0, 7, "1"),
    ONE2(1, 9, "0"),
    ONE3(2, 10, "X"),
    ONE4(3, 5, "9"),
    ONE5(4, 8, "8"),
    ONE6(5, 4, "7"),
    ONE7(6, 2, "6"),
    ONE8(7, 1, "5"),
    ONE9(8, 6, "4"),
    ONE10(9, 3, "3"),
    ONE11(10, 7, "2"),
    ONE12(11, 9, ""),
    ONE13(12, 10, ""),
    ONE14(13, 5, ""),
    ONE15(14, 8, ""),
    ONE16(15, 4, ""),
    ONE17(16, 2, ""),
    ;

    private final int index;

    private final int factor;

    private final String otp;

    IdEnum(int index, int factor, String otp) {
        this.index = index;
        this.factor = factor;
        this.otp = otp;
    }

    public static Integer getFactor(Integer index) {
        return IdHolder.map.get(index);
    }

    public static String getOtp(Integer index) {
        return IdHolder.map2.get(index);
    }


    private static class IdHolder {
        private final static Map<Integer, Integer> map = new HashMap<>(32);
        private final static Map<Integer, String> map2 = new HashMap<>(32);

        static {
            Arrays.stream(IdEnum.values()).forEach(item -> {
                map.put(item.index, item.factor);
                map2.put(item.index, item.otp);
            });
        }
    }
}
