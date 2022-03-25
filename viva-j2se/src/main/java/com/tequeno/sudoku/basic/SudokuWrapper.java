package com.tequeno.sudoku.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格处理类
 */
public class SudokuWrapper {

    /**
     * 对应行数据
     */
    private List<SudokuDto> listX = new ArrayList<>();

    /**
     * 对应列数据
     */
    private List<SudokuDto> listY = new ArrayList<>();

    /**
     * 对应单个的九宫格
     */
    private List<SudokuDto> listZ = new ArrayList<>();

    public void init() {
        for (int i = 0; i < 9; i++) {
            listX.add(new SudokuDto());
            listY.add(new SudokuDto());
            listZ.add(new SudokuDto());
        }
    }

    public List<SudokuDto> getListX() {
        return listX;
    }

    public void setListX(List<SudokuDto> listX) {
        this.listX = listX;
    }

    public List<SudokuDto> getListY() {
        return listY;
    }

    public void setListY(List<SudokuDto> listY) {
        this.listY = listY;
    }

    public List<SudokuDto> getListZ() {
        return listZ;
    }

    public void setListZ(List<SudokuDto> listZ) {
        this.listZ = listZ;
    }

    public void fullFillWrapper() {
        // 2、利用zi属性循环获得所有Y坐标的Rule(此处zi只作为引用,因为顺序是0-8)
        for (int index : Constants.INDEXES) {
            List<Position> positionList = Position.YPositions(index);
            SudokuDto dto = listY.get(index);
            for (Position position : positionList) {
                // 获取X对应坐标下的值填充给Y坐标
                dto.nums[position.getX()] = listX.get(position.getX()).nums[position.getY()];
            }

            positionList = Position.ZPositions(index);
            dto = listZ.get(index);
            for (Position position : positionList) {
                // 获取X对应坐标下的值填充给Y坐标
                dto.nums[position.getZp()] = listX.get(position.getX()).nums[position.getY()];
            }
        }
    }

    public void printInfoToConsole() {
        System.out.println("-----------------X坐标----------------------------");
        for (SudokuDto dto : listX) {
            for (int i : dto.nums) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------Y坐标----------------------------");
        for (SudokuDto dto : listY) {
            for (int i : dto.nums) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------九宫格---------------------------");
        for (SudokuDto dto : listZ) {
            for (int i : dto.nums) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
