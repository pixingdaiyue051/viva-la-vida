package com.jdk8.cstream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        List<Integer> num1 = Arrays.asList(1, 4, 7);
        List<Integer> num2 = Arrays.asList(2, 5, 8);
        List<Integer> num3 = Arrays.asList(3, 6, 9);
        System.out.println("平方");
        num1.stream()
                .mapToInt(i -> i * i)
                .forEach(System.out::print);
        System.out.println();
        System.out.println("两层嵌套遍历累加");
        num1.stream()
                .distinct()
                .flatMap(i -> num2.stream()
                        .distinct()
                        .map(j -> new int[]{i, j}))
                .forEach(num -> System.out.println(num[0] + "," + num[1]));
        System.out.println("三层嵌套遍历累加");
        num1.stream()
                .distinct()
                .flatMap(i -> num2.stream()
                        .distinct()
                        .flatMap(j -> num3.stream()
                                .distinct()
//                                .filter(k -> (i + j + k) % 5 == 0)
                                .map(k -> new int[]{i, j, k})))
                .forEach(num -> System.out.println(num[0] + "," + num[1] + "," + num[2]));
        System.out.println("求和");
        Integer count = num1.stream()
                .reduce(0, Integer::sum);
        System.out.println(count);
        System.out.println("求最大值");
        num1.stream()
                .reduce(Integer::max)
                .ifPresent(System.out::println);
        System.out.println("求最小值");
        num1.stream()
                .reduce(Integer::min)
                .ifPresent(System.out::println);
        System.out.println("静态方法range,相当于[1,10)");
        IntStream.range(1, 10)
                .boxed()// 封箱操作
                .forEach(System.out::print);
        System.out.println();
        System.out.println("静态方法range,相当于[1,10]");
        IntStream.rangeClosed(1, 10)
                .boxed()
                .forEach(System.out::print);
        System.out.println();
        System.out.println("查找100以内的勾股数");
        System.out.println("1、需要做两遍开方算法");
        IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a + 1, 100)
                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .boxed()
                        .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}))
                .forEach(v -> System.out.println(v[0] + "," + v[1] + "," + v[2]));
        System.out.println("2、先组成三元组再做一遍开方算法");
        IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a + 1, 100)
                        .boxed()
                        .map(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
//                        .filter(v -> v[2] < 100d)
                        .filter(v -> v[2] % 1 == 0))
                .forEach(v -> System.out.println((int) v[0] + "," + (int) v[1] + "," + (int) v[2]));
        System.out.println("静态方法生成流");
        int sum = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .mapToInt(d -> d)
                .sum();
        System.out.println(sum);

        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int sum1 = Arrays.stream(nums)
                .sum();
        System.out.println(sum1);
        System.out.println("利用无限流生成斐波那契亚数列");
        Stream.iterate(new int[]{0, 1}, v -> new int[]{v[1], v[0] + v[1]})
                .limit(15)
                .mapToInt(v -> v[0])
                .forEach(System.out::print);

    }
}