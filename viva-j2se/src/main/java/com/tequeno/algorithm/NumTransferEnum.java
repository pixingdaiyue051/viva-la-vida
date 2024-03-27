package com.tequeno.algorithm;

/**
 * 数字进制
 */
public enum NumTransferEnum {
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

    NumTransferEnum(int dec, int disp, String reg) {
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
