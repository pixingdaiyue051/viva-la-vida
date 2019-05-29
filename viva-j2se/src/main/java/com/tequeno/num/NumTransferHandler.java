package com.tequeno.num;

/**
 * 数值进制转换
 *
 * @author hexk
 */
public class NumTransferHandler {

    /**
     * 统一的转换方法
     * 未做数值进制格式验证
     *
     * @param in   待转换的输入数值
     * @param from 输入值的进制
     * @param to   输出值的进制
     * @return 转换值
     */
    public String transfer(String in, DecDispEnum from, DecDispEnum to) {
        boolean matches = in.matches(from.getReg());
        if(!matches){
            return "数字格式不正确";
        }
        if (from.equals(to)) {
            return in;
        }
        StringBuilder builder = new StringBuilder();
        if (DecDispEnum.DEC.equals(from)) {
            decodeDec2Value(builder, Integer.parseInt(in), to);
            return builder.toString();
        }
        if (DecDispEnum.DEC.equals(to)) {
            int dec = decodeValue2Dec(in, from);
            return String.valueOf(dec);
        }
        int dec = decodeValue2Dec(in, from);
        decodeDec2Value(builder, dec, to);
        return builder.toString();
    }

    private void decodeDec2Value(StringBuilder builder, int key, DecDispEnum decDispEnum) {
        int factor = key / decDispEnum.getDec();
        int tmp = key - factor * decDispEnum.getDec();
        if (factor > 0) {
            decodeDec2Value(builder, factor, decDispEnum);
        }
        transfer2FromIndex(builder, tmp);
    }

    private void transfer2FromIndex(StringBuilder builder, int index) {
        switch (index) {
            case 10:
                builder.append("a");
                break;
            case 11:
                builder.append("b");
                break;
            case 12:
                builder.append("c");
                break;
            case 13:
                builder.append("d");
                break;
            case 14:
                builder.append("e");
                break;
            case 15:
                builder.append("f");
                break;
            default:
                builder.append(index);
        }
    }

    private int decodeValue2Dec(String key, DecDispEnum decDispEnum) {
        String[] split = key.split("");
        int result = 0;
        for (int i = 0; i < split.length; i++) {
            int pow = decDispEnum.getDisp() * (split.length - i - 1);
            result += transfer2FromDataStr(split[i]) * (1 << pow);
        }
        return result;
    }

    private int transfer2FromDataStr(String data) {
        switch (data) {
            case "a":
                return 10;
            case "b":
                return 11;
            case "c":
                return 12;
            case "d":
                return 13;
            case "e":
                return 14;
            case "f":
                return 15;
            default:
                return Integer.parseInt(data);
        }
    }

    /**
     * 数字进制
     */
    public enum DecDispEnum {
        /**
         * 二进制
         */
        BIN(2, 1, "[01]+"),
        /**
         * 八进制
         */
        OCT(8, 3, "[0-7]+"),
        /**
         * 十六进制
         */
        HEX(16, 4, "[0-9a-f]+"),
        /**
         * 十进制
         */
        DEC(10, 0, "[0-9]+");

        private int dec;
        private int disp;
        private String reg;

        DecDispEnum(int dec, int disp, String reg) {
            this.dec = dec;
            this.disp = disp;
            this.reg = reg;
        }

        public int getDec() {
            return dec;
        }

        public int getDisp() {
            return disp;
        }

        public String getReg() {
            return reg;
        }
    }
}
