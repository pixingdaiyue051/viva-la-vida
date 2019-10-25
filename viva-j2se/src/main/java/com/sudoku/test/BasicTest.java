package com.sudoku.test;

import com.sudoku.basic.IndexDataBinder;
import com.sudoku.basic.SudokuDto;
import com.sudoku.basic.SudokuWrapper;
import com.sudoku.checker.SudokuChecker;
import com.sudoku.generator.SudokuGenerator;
import com.sudoku.resolver.SudokuResolver;

import java.util.List;

public class BasicTest {

    public static void main(String[] args) {
//        testCheckMethod();
//        testGenerateMethod();
        testResolveMethod();
    }

    private static void testResolveMethod() {
        // 生成
        SudokuGenerator generator = new SudokuGenerator();
        SudokuWrapper wrapper = generator.generate(10);
        wrapper.printInfoToConsole();

        // 解决
        SudokuResolver resolver = new SudokuResolver();
        long l1 = System.currentTimeMillis();
        resolver.resolve(wrapper);
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1);
//        wrapper.printInfoToConsole();

        // 校验
//        SudokuChecker checker = new SudokuChecker();
//        boolean checked = checker.check(wrapper);
//        System.out.println(checked);
    }

    private static void testGenerateMethod() {
        SudokuGenerator generator = new SudokuGenerator();
        long l1 = System.currentTimeMillis();
        SudokuWrapper wrapper = generator.generate(10);
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1);
        wrapper.printInfoToConsole();
    }

    private static void testCheckMethod() {
        SudokuGenerator generator = new SudokuGenerator();
        SudokuDto dto0 = generator.generateSudokuDto(1, 5, 2, 6, 8, 3, 9, 4, 7);//1, 5, 2, 6, 8, 3, 9, 4, 7
        SudokuDto dto1 = generator.generateSudokuDto(9, 8, 4, 5, 1, 7, 3, 2, 6);//9, 8, 4, 5, 1, 7, 3, 2, 6
        SudokuDto dto2 = generator.generateSudokuDto(7, 3, 6, 2, 9, 4, 8, 5, 1);//7, 3, 6, 2, 9, 4, 8, 5, 1
        SudokuDto dto3 = generator.generateSudokuDto(8, 1, 7, 3, 4, 9, 2, 6, 5);//8, 1, 7, 3, 4, 9, 2, 6, 5
        SudokuDto dto4 = generator.generateSudokuDto(2, 4, 9, 1, 6, 5, 7, 8, 3);//2, 4, 9, 1, 6, 5, 7, 8, 3
        SudokuDto dto5 = generator.generateSudokuDto(5, 6, 3, 7, 2, 8, 1, 9, 4);//5, 6, 3, 7, 2, 8, 1, 9, 4
        SudokuDto dto6 = generator.generateSudokuDto(3, 7, 8, 9, 5, 6, 4, 1, 2);//3, 7, 8, 9, 5, 6, 4, 1, 2
        SudokuDto dto7 = generator.generateSudokuDto(4, 2, 5, 8, 7, 1, 6, 3, 9);//4, 2, 5, 8, 7, 1, 6, 3, 9
        SudokuDto dto8 = generator.generateSudokuDto(6, 9, 1, 4, 3, 2, 5, 7, 8);//6, 9, 1, 4, 3, 2, 5, 7, 8

        SudokuWrapper wrapper = new SudokuWrapper();
        wrapper.init();
        List<SudokuDto> listX = wrapper.getListX();
        listX.clear();
        listX.add(dto0);
        listX.add(dto1);
        listX.add(dto2);
        listX.add(dto3);
        listX.add(dto4);
        listX.add(dto5);
        listX.add(dto6);
        listX.add(dto7);
        listX.add(dto8);

        wrapper.fullFillWrapper();
        wrapper.printInfoToConsole();

        SudokuChecker checker = new SudokuChecker();
        long l1 = System.currentTimeMillis();
        boolean checked = checker.check(wrapper);
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1);
        System.out.println(checked);
        if (!checked) {
            long l3 = System.currentTimeMillis();
            List<IndexDataBinder> binders = checker.checkBuffer(wrapper);
            long l4 = System.currentTimeMillis();
            System.out.println(l4 - l3);
            for (IndexDataBinder d : binders) {
                d.printInfoToConsole();
            }
        }
    }

}
