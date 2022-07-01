package com.tequeno.test;

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
    public String transfer(String in, NumTransferEnum from, NumTransferEnum to) {
        boolean matches = in.matches(from.getReg());
        if(!matches){
            return "数字格式不正确";
        }
        if (from.equals(to)) {
            return in;
        }
        StringBuilder builder = new StringBuilder();
        if (NumTransferEnum.DEC.equals(from)) {
            decodeDec2Value(builder, Integer.parseInt(in), to);
            return builder.toString();
        }
        if (NumTransferEnum.DEC.equals(to)) {
            int dec = decodeValue2Dec(in, from);
            return String.valueOf(dec);
        }
        int dec = decodeValue2Dec(in, from);
        decodeDec2Value(builder, dec, to);
        return builder.toString();
    }

    private void decodeDec2Value(StringBuilder builder, int key, NumTransferEnum numTransferEnum) {
        int factor = key / numTransferEnum.getDec();
        int tmp = key - factor * numTransferEnum.getDec();
        if (factor > 0) {
            decodeDec2Value(builder, factor, numTransferEnum);
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

    private int decodeValue2Dec(String key, NumTransferEnum numTransferEnum) {
        String[] split = key.split("");
        int result = 0;
        for (int i = 0; i < split.length; i++) {
            int pow = numTransferEnum.getDisp() * (split.length - i - 1);
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

}
