package com.tequeno.jdk8.cstream;

import java.util.*;
import java.util.stream.Collectors;

public class DishTest {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("meat1", false, 100, Dish.Type.MEAT),
                new Dish("meat2", false, 200, Dish.Type.MEAT),
                new Dish("meat3", false, 400, Dish.Type.MEAT),
                new Dish("meat4", false, 500, Dish.Type.MEAT),
                new Dish("meat5", false, 700, Dish.Type.MEAT),
                new Dish("meat6", false, 800, Dish.Type.MEAT),
                new Dish("food1", true, 100, Dish.Type.OTHER),
                new Dish("food2", true, 200, Dish.Type.OTHER),
                new Dish("food3", true, 400, Dish.Type.OTHER),
                new Dish("food4", true, 500, Dish.Type.OTHER),
                new Dish("food5", true, 700, Dish.Type.OTHER),
                new Dish("food6", true, 800, Dish.Type.OTHER),
                new Dish("fish1", false, 100, Dish.Type.FISH),
                new Dish("fish2", false, 200, Dish.Type.FISH),
                new Dish("fish3", false, 400, Dish.Type.FISH),
                new Dish("fish4", false, 500, Dish.Type.FISH),
                new Dish("fish5", false, 700, Dish.Type.FISH),
                new Dish("fish6", false, 800, Dish.Type.FISH));

        System.out.println("获取卡路里大于400的菜名");
        menu.stream().filter(d -> d.getCalories() > 400)
                .distinct()
                .collect(Collectors.toList())
                .forEach(d -> System.out.println(d.getName()));

        System.out.println("获取第一道蔬菜的菜名");
        menu.stream()
                .filter(Dish::isVegetarian)
                .map(Dish::getName)
                .distinct()
                .limit(1)
                // 返回值不是流即为终结操作，关闭后无法再次使用
                .forEach(System.out::println);

        System.out.println("是否有蔬菜");
        boolean b = menu.stream().anyMatch(Dish::isVegetarian);
        System.out.println(b);

        System.out.println("是否全部是蔬菜");
        b = menu.stream().allMatch(Dish::isVegetarian);
        System.out.println(b);

        System.out.println("是否全部不是蔬菜");
        b = menu.stream().noneMatch(Dish::isVegetarian);
        System.out.println(b);

        System.out.println("任意输出一道蔬菜的菜名");
        menu.stream()
                .filter(Dish::isVegetarian)
                .map(Dish::getName)
                .findAny()
                .ifPresent(System.out::println);

        System.out.println("按卡里路顺序排序所有蔬菜，输出第一道菜名");
        menu.stream()
                .filter(Dish::isVegetarian)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .findFirst()
                .ifPresent(System.out::println);

        System.out.println("使用map-reduce模式做数据处理");
        int count = menu.stream()
                .map(d -> 1)
                .reduce(0, Integer::sum);
        System.out.println(count);
        long count1 = menu.stream().count();// 这两种处理结果是一致的
        System.out.println(count1);
        long sum = menu.stream().mapToLong(m -> 1L).sum();// count方法的实现
        System.out.println(sum);

        System.out.println("Collectors.toList 的使用");
        menu.stream()
                .map(m -> m.getName())
                .distinct()
                .collect(Collectors.toList())
                .forEach(System.out::println);

        System.out.println("Collectors.toSet 的使用");
        menu.stream()
                .map(m -> m.getName())
                .collect(Collectors.toSet())
                .forEach(System.out::println);

        System.out.println("Collectors.toMap 的使用");
        menu.stream()
                .collect(Collectors.toMap(Dish::getName, Dish::getCalories))
                .forEach((k, v) -> {
                    System.out.print(k + "----");
                    System.out.println(v);
                });

        System.out.println("Collectors.joining 的使用");
        System.out.println("不带参数收尾连接");
        String s = menu.stream()
                .map(m -> m.getName())
                .distinct()
                .collect(Collectors.joining());
        System.out.println(s);
        System.out.println("传入分隔符连接");
        s = menu.stream()
                .map(m -> m.getName())
                .distinct()
                .collect(Collectors.joining(","));
        System.out.println(s);
        System.out.println("传入分隔符和前缀后缀连接");
        s = menu.stream()
                .map(m -> m.getName())
                .distinct()
                .collect(Collectors.joining(",", "", "."));
        System.out.println(s);
        System.out.println("Collectors.counting 的使用");
        Long aLong = menu.stream()
                .collect(Collectors.counting());
        System.out.println(aLong);
        aLong = menu.stream()
                .count();
        System.out.println(aLong);

        System.out.println("Collectors.summingInt 的使用,获得总计");
        Integer ic = menu.stream()
//                .mapToInt(Dish::getCalories)
//                .sum();
                .collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(ic);

        System.out.println("Collectors.maxBy minBy sorted findFirst 的使用,获得最大最小值");
        System.out.println("输入最大卡里路的菜名");
        menu.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)))
                .ifPresent(m -> System.out.println(m.getName()));
//        menu.stream()
//                .mapToInt(Dish::getCalories)
//                .max()
//                .ifPresent(System.out::println);
        menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories).reversed())
                .findFirst()
                .ifPresent(m -> System.out.println(m.getName()));
        System.out.println("输入最小卡里路的菜名");
        menu.stream()
                .collect(Collectors.minBy(Comparator.comparing(Dish::getCalories)))
                .ifPresent(m -> System.out.println(m.getName()));
//        menu.stream()
//                .mapToInt(Dish::getCalories)
//                .min()
//                .ifPresent(System.out::println);
        menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories))
                .findFirst()
                .ifPresent(m -> System.out.println(m.getName()));

        System.out.println("Collectors.averagingInt 的使用,获得平均值");
        Double ic1 = menu.stream()
                .collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(ic1);

        System.out.println("Collectors.summarizingInt 的使用,获得总计，最大最小，平均，统计数量");
        IntSummaryStatistics summaryStatistics = menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(summaryStatistics);

        System.out.println("Collectors.groupingBy 的使用");
        System.out.println("按类别统计，输出各类别下菜名");
        menu.stream()
                .collect(Collectors.groupingBy(Dish::getType))
                .forEach((k, v) -> {
                    System.out.print(k + "-------");
                    System.out.println(v);
                });
        System.out.println("按卡路里统计，输出各等级里菜名");
        menu.stream()
                .collect(Collectors.groupingBy(d -> {
                    if (d.getCalories() < 300) {
                        return "low<300";
                    } else if (d.getCalories() < 600) {
                        return "300<mid<600";
                    } else {
                        return "high>600";
                    }
                }))
                .forEach((k, v) -> {
                    System.out.print(k + "-------");
                    System.out.println(v);
                });
        System.out.println("二级分组");
        menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(d -> {
                            if (d.getCalories() < 300) {
                                return "low<300";
                            } else if (d.getCalories() < 600) {
                                return "300<mid<600";
                            } else {
                                return "high>600";
                            }
                        })))
                .forEach((k, v) -> {
                    System.out.print(k + "------");
                    System.out.println(v);
                });

        System.out.println("按是否蔬菜统计，输出各类别下菜名");
        menu.stream()
                .collect(Collectors.partitioningBy((Dish::isVegetarian),
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)))
                .forEach((k, v) -> {
                    System.out.print(k + "-------");
                    System.out.println(v);
                });
    }
}