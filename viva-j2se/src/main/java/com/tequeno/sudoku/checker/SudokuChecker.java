package com.tequeno.sudoku.checker;

import com.tequeno.sudoku.basic.Constants;
import com.tequeno.sudoku.basic.IndexDataBinder;
import com.tequeno.sudoku.basic.SudokuDto;
import com.tequeno.sudoku.basic.SudokuWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验算法类
 */
public class SudokuChecker {

    /**
     * 简单验证是否有重复
     *
     * @param wrapper
     * @return
     */
    public boolean check(SudokuWrapper wrapper) {
        List<SudokuDto> listX = wrapper.getListX();
        List<SudokuDto> listY = wrapper.getListY();
        List<SudokuDto> listZ = wrapper.getListZ();

        for (int index : Constants.INDEXES) {
            SudokuDto dto = listX.get(index);
            if (!dto.selfCheck()) {
                return false;
            }
            dto = listY.get(index);
            if (!dto.selfCheck()) {
                return false;
            }
            dto = listZ.get(index);
            if (!dto.selfCheck()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证重复并且打印出重复数的坐标
     *
     * @param wrapper
     */
    public List<IndexDataBinder> checkBuffer(SudokuWrapper wrapper) {
        List<SudokuDto> listX = wrapper.getListX();
        List<SudokuDto> listY = wrapper.getListY();
        List<SudokuDto> listZ = wrapper.getListZ();

        List<IndexDataBinder> result = new ArrayList<>();
        for (int index : Constants.INDEXES) {
            SudokuDto dto = listX.get(index);
            List<IndexDataBinder> list = dto.selfCheckBufferX(index);
            result.addAll(list);
            dto = listY.get(index);
            list = dto.selfCheckBufferY(index);
            result.addAll(list);
            dto = listZ.get(index);
            list = dto.selfCheckBufferZ(index);
            result.addAll(list);
        }
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }
}
