package com.tequeno.sudoku.generator;

import com.tequeno.sudoku.basic.*;
import com.tequeno.sudoku.checker.SudokuChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {

    /**
     * 生成单独的一个行数据
     *
     * @param num0
     * @param num1
     * @param num2
     * @param num3
     * @param num4
     * @param num5
     * @param num6
     * @param num7
     * @param num8
     * @return
     */
    public SudokuDto generateSudokuDto(int num0, int num1, int num2, int num3, int num4, int num5, int num6, int num7, int num8) {
        SudokuDto dto = new SudokuDto(num0, num1, num2, num3, num4, num5, num6, num7, num8);
        return dto;
    }

    public SudokuWrapper generate(int count) {
        SudokuChecker checker = new SudokuChecker();
        SudokuWrapper wrapper;
        while (true) {
            wrapper = new SudokuWrapper();// 获得数独处理类
            wrapper.init();// 初始化参数
            List<IndexDataBinder> list = randomToGetNums(count);// 按照入参初始化随机数
            for (IndexDataBinder binder : list) {
                SudokuDto dto = wrapper.getListX().get(binder.getX());
                dto.nums[binder.getY()] = binder.getNum();// 把随机数填入X坐标

                dto = wrapper.getListY().get(binder.getY());
                dto.nums[binder.getX()] = binder.getNum();// 把随机数填入Y坐标

                Position position = Position.getPositionByXY(binder.getX(), binder.getY());
                dto = wrapper.getListZ().get(position.getZi());
                dto.nums[position.getZp()] = binder.getNum();// 把随机数填入Z坐标
            }
//            wrapper.fullFillWrapper();// 按照X坐标填满Y和Z坐标
            if (checker.check(wrapper)) {// 校验
                break;
            }
        }
        return wrapper;
    }

    private List<IndexDataBinder> randomToGetNums(int count) {
        List<IndexDataBinder> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            while (true) {
                int x = r.nextInt(Constants.NUMLENGTH);
                int y = r.nextInt(Constants.NUMLENGTH);
                int num = r.nextInt(Constants.NUMLENGTH) + 1;
                IndexDataBinder binder = new IndexDataBinder(x, y, num);
                if (!isExist(list, binder)) {
                    list.add(binder);
                    break;
                }
            }
        }
        return list;
    }

    private boolean isExist(List<IndexDataBinder> list, IndexDataBinder binder) {
        for (IndexDataBinder b : list) {
            if (b.equals(binder)) {
                return true;
            }
        }
        return false;
    }
}
