package com.tequeno.sudoku.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * 数独校验规则索引列表
 */
public enum Position {

    P00(0, 0, 0, 0),
    P01(0, 1, 0, 1),
    P02(0, 2, 0, 2),
    P10(0, 3, 1, 0),
    P11(0, 4, 1, 1),
    P12(0, 5, 1, 2),
    P20(0, 6, 2, 0),
    P21(0, 7, 2, 1),
    P22(0, 8, 2, 2),

    P03(1, 0, 0, 3),
    P04(1, 1, 0, 4),
    P05(1, 2, 0, 5),
    P13(1, 3, 1, 3),
    P14(1, 4, 1, 4),
    P15(1, 5, 1, 5),
    P23(1, 6, 2, 3),
    P24(1, 7, 2, 4),
    P25(1, 8, 2, 5),

    P06(2, 0, 0, 6),
    P07(2, 1, 0, 7),
    P08(2, 2, 0, 8),
    P16(2, 3, 1, 6),
    P17(2, 4, 1, 7),
    P18(2, 5, 1, 8),
    P26(2, 6, 2, 6),
    P27(2, 7, 2, 7),
    P28(2, 8, 2, 8),

    P30(3, 0, 3, 0),
    P31(3, 1, 3, 1),
    P32(3, 2, 3, 2),
    P40(3, 3, 4, 0),
    P41(3, 4, 4, 1),
    P42(3, 5, 4, 2),
    P50(3, 6, 5, 0),
    P51(3, 7, 5, 1),
    P52(3, 8, 5, 2),

    P33(4, 0, 3, 3),
    P34(4, 1, 3, 4),
    P35(4, 2, 3, 5),
    P43(4, 3, 4, 3),
    P44(4, 4, 4, 4),
    P45(4, 5, 4, 5),
    P53(4, 6, 5, 3),
    P54(4, 7, 5, 4),
    P55(4, 8, 5, 5),

    P36(5, 0, 3, 6),
    P37(5, 1, 3, 7),
    P38(5, 2, 3, 8),
    P46(5, 3, 4, 6),
    P47(5, 4, 4, 7),
    P48(5, 5, 4, 8),
    P56(5, 6, 5, 6),
    P57(5, 7, 5, 7),
    P58(5, 8, 5, 8),

    P60(6, 0, 6, 0),
    P61(6, 1, 6, 1),
    P62(6, 2, 6, 2),
    P70(6, 3, 7, 0),
    P71(6, 4, 7, 1),
    P72(6, 5, 7, 2),
    P80(6, 6, 8, 0),
    P81(6, 7, 8, 1),
    P82(6, 8, 8, 2),

    P63(7, 0, 6, 3),
    P64(7, 1, 6, 4),
    P65(7, 2, 6, 5),
    P73(7, 3, 7, 3),
    P74(7, 4, 7, 4),
    P75(7, 5, 7, 5),
    P83(7, 6, 8, 3),
    P84(7, 7, 8, 4),
    P85(7, 8, 8, 5),

    P66(8, 0, 6, 6),
    P67(8, 1, 6, 7),
    P68(8, 2, 6, 8),
    P76(8, 3, 7, 6),
    P77(8, 4, 7, 7),
    P78(8, 5, 7, 8),
    P86(8, 6, 8, 6),
    P87(8, 7, 8, 7),
    P88(8, 8, 8, 8),
    ;

    /**
     * 九宫格索引(自左向右,由上而下)
     */
    private final int zi;
    /**
     * 对应九宫格内每一个格子的索引(自左向右,由上而下)
     */
    private final int zp;
    /**
     * 对应行数
     */
    private final int x;
    /**
     * 对应列数
     */
    private final int y;

    Position(int zi, int zp, int x, int y) {
        this.zi = zi;
        this.zp = zp;
        this.x = x;
        this.y = y;
    }

    public int getZi() {
        return zi;
    }

    public int getZp() {
        return zp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static List<Position> XPositions(int xIndex) {
        List<Position> positionList = new ArrayList<>();
        for (Position position : Position.values()) {
            if (position.getX() == xIndex) {
                positionList.add(position);
            }
        }
        return positionList;
    }

    public static List<Position> YPositions(int yIndex) {
        List<Position> positionList = new ArrayList<>();
        for (Position position : Position.values()) {
            if (position.getY() == yIndex) {
                positionList.add(position);
            }
        }
        return positionList;
    }

    public static List<Position> ZPositions(int ziIndex) {
        List<Position> positionList = new ArrayList<>();
        for (Position position : Position.values()) {
            if (position.getZi() == ziIndex) {
                positionList.add(position);
            }
        }
        return positionList;
    }

    public static Position getPositionByXY(int xIndex, int yIndex) {
        return XPositions(xIndex).get(yIndex);
    }

    public static Position getPositionByZ(int ziIndex, int zpIndex) {
        return ZPositions(ziIndex).get(zpIndex);
    }
}
