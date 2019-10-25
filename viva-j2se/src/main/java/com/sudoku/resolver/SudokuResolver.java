package com.sudoku.resolver;

import com.ht.basic.Position;
import com.ht.basic.SudokuDto;
import com.ht.basic.SudokuWrapper;

import java.util.*;

/**
 * 校验算法类
 */
public class SudokuResolver {

    private final static int[] nums2BeFill = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    private final static Map<Position, LinkedList<String>> pool = new HashMap<>();

    private List<SudokuDto> listX;
    private List<SudokuDto> listY;
    private List<SudokuDto> listZ;

    /**
     * 简单验证是否有重复
     *
     * @param wrapper
     * @return
     */
    public boolean resolve(SudokuWrapper wrapper) {
        listX = wrapper.getListX();
        listY = wrapper.getListY();
        listZ = wrapper.getListZ();
        Position[] positions = Position.values();

        for (Position p : positions) {
            SudokuDto dtoX = listX.get(p.getX());
            int tmp = dtoX.nums[p.getY()];
            if (tmp < 1) {
                SudokuDto dtoY = listY.get(p.getY());
                SudokuDto dtoZ = listZ.get(p.getZi());
                LinkedList<String> list = new LinkedList<>();
                for (int n : nums2BeFill) {
                    if (dtoX.minorCheck(n) && dtoY.minorCheck(n) && dtoZ.minorCheck(n))
                        list.add(String.valueOf(n));
                }
                pool.put(p, list);
            }
        }
        pool.entrySet().stream().sorted();
        pool.forEach((k, v) -> {
            System.out.print(k + ":");
            v.forEach(System.out::print);
            System.out.println();
        });
//        takeMe(pool, 0);
//        wrapper.fullFillWrapper();
        return true;
    }

    private void takeMe(Map<Position, LinkedList<String>> pool, int startIndex) {
//        for (int i = startIndex; i < pool.length; i++) {
//            int indexX = pool[i].getX();
//            int indexY = pool[i].getY();
//            int indexZi = pool[i].getZi();
//            int indexZp = pool[i].getZp();
//            SudokuDto dtoX = listX.get(indexX);
//            SudokuDto dtoY = listY.get(indexY);
//            SudokuDto dtoZ = listZ.get(indexZi);
//            int tmp = dtoX.nums[indexY];
//            if (tmp < 1) {
//                LinkedList<Integer> linkedList = SudokuResolver.pool.get(pool[i]);
//                boolean isReCursive = true;
//                for (int n : linkedList) {
//                    // X行自检 Y列自检 Z方格自检
//                    if (dtoX.minorCheck(n, indexY) && dtoY.minorCheck(n, indexX) && dtoZ.minorCheck(n, indexZp)) {
//                        // 通过了这三个自检后说明该数字可用，填完后退出循环
//                        dtoX.nums[indexY] = n;
//                        dtoY.nums[indexX] = n;
//                        dtoZ.nums[indexZp] = n;
//                        linkedList.remove(n - 1);
//                        isReCursive = false;
//                        break;
//                    }
//                }
//                // 如果没有找到合适的数字，说明无解，则回退到上一步重新填写
//                if (isReCursive) {
//                    takeMe(listX, listY, listZ, pool, i - 1);
//                    return;
//                }
//            }
//        }
    }

}
