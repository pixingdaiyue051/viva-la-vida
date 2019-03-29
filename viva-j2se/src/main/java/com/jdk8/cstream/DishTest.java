package com.jdk8.cstream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DishTest {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        menu.stream().filter(d -> d.getCalories() > 400).collect(Collectors.toList()).forEach(d -> System.out.println(d.getName()));
        Stream<String> stream = menu.stream().filter(Dish::isVegetarian).map(Dish::getName).distinct();
        stream.limit(1).forEach(System.out::println);
        stream.skip(1).forEach(System.out::println);// 使用了终结操作，流已经关闭无法再使用
    }
}
