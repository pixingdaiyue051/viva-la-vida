package com.sudoku.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据封装类,主要用作规则校验
 */
public class SudokuDto {
    public int[] nums = new int[9];

    public SudokuDto() {
    }

    public SudokuDto(int num0, int num1, int num2, int num3, int num4, int num5, int num6, int num7, int num8) {
        nums[0] = num0;
        nums[1] = num1;
        nums[2] = num2;
        nums[3] = num3;
        nums[4] = num4;
        nums[5] = num5;
        nums[6] = num6;
        nums[7] = num7;
        nums[8] = num8;
    }

    /**
     * 循环自校验数据是否重复
     *
     * @return
     */
    public boolean selfCheck() {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == 0) {
                    continue;
                }
                if (nums[i] == nums[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 循环自校验数据是否重复
     *
     * @param sudokuIndex
     * @return
     */
    public List<IndexDataBinder> selfCheckBufferX(int sudokuIndex) {
        List<IndexDataBinder> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    result.add(new IndexDataBinder(sudokuIndex, i, nums[i]));
                    result.add(new IndexDataBinder(sudokuIndex, j, nums[i]));
                }
            }
        }
        return result;
    }

    /**
     * 循环自校验数据是否重复
     *
     * @param sudokuIndex
     * @return
     */
    public List<IndexDataBinder> selfCheckBufferY(int sudokuIndex) {
        List<IndexDataBinder> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    result.add(new IndexDataBinder(i, sudokuIndex, nums[i]));
                    result.add(new IndexDataBinder(j, sudokuIndex, nums[i]));
                }
            }
        }
        return result;
    }

    /**
     * 循环自校验数据是否重复
     *
     * @param sudokuIndex
     * @return
     */
    public List<IndexDataBinder> selfCheckBufferZ(int sudokuIndex) {
        List<IndexDataBinder> result = new ArrayList<>();
        // X坐标加权值012加权0,345加权3,678加权6
        int ixWeight = Constants.ENTERDIVISOR * (sudokuIndex / Constants.ENTERDIVISOR);
        // Y坐标加权值036加权0,147加权3,258加权6
        int iyWeight = Constants.ENTERDIVISOR * (sudokuIndex - ixWeight);
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    int ix = i / Constants.ENTERDIVISOR;
                    int iy = i - ix * Constants.ENTERDIVISOR;
                    result.add(new IndexDataBinder(ixWeight + ix, iyWeight + iy, nums[i]));
                    ix = j / Constants.ENTERDIVISOR;
                    iy = j - ix * Constants.ENTERDIVISOR;
                    result.add(new IndexDataBinder(ixWeight + ix, iyWeight + iy, nums[i]));
                }
            }
        }
        return result;
    }

    /**
     * 镜像校验数据
     * 算法有问题待改进
     *
     * @param numToBeCheck 待检验的数
     * @param numIndex     检验行里不需要检验的数的下标
     * @return
     */
    public boolean minorCheck(int numToBeCheck, int numIndex) {
        boolean result = true;
        int tmp = nums[numIndex];
        nums[numIndex] = 0;
        for (int num : nums) {
            if (num == 0) {
                continue;
            }
            if (num == numToBeCheck) {
                result = false;
                break;
            }
        }
        nums[numIndex] = tmp;
        return result;
    }

    public boolean minorCheck(int numToBeCheck) {
        boolean result = true;
        for (int num : nums) {
            if (num == 0) {
                continue;
            }
            if (num == numToBeCheck) {
                result = false;
                break;
            }
        }
        return result;
    }
}
