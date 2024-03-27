package com.tequeno;

import com.tequeno.jdk8.stream.Dish;
import com.tequeno.jdk8.stream.Trader;
import com.tequeno.jdk8.stream.Transaction;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {@Test
public void testDish() {
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
    @Test
    public void testTrader() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        System.out.println("1、2011年发生的所有交易，并按交易额升序排序");
        transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .forEach(t -> System.out.println(t.toString()));
        System.out.println("2、交易员都在哪些不同的城市工作过");
        Arrays.stream(new Trader[]{raoul, mario, alan, brian})
                .map(t -> t.getCity())
                .distinct()
                .forEach(System.out::println);
        System.out.println("3、所有来自于剑桥的交易员，并按姓名排序");
        Arrays.stream(new Trader[]{raoul, mario, alan, brian})
                .filter(t -> "Cambridge".equals(t.getCity()))
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(t -> System.out.println(t.toString()));
        System.out.println("4、所有交易员的姓名字符串，按字母顺序排序");
        Arrays.stream(new Trader[]{raoul, mario, alan, brian})
                .map(Trader::getName)
                .sorted()
                .forEach(System.out::println);
        System.out.println("5、有没有交易员是在米兰工作的");
        boolean match = Arrays.stream(new Trader[]{raoul, mario, alan, brian})
                .anyMatch(t -> "Milan".equals(t.getCity()));
        System.out.println(match);
        System.out.println("6、生活在剑桥的交易员的所有交易额");
        Integer sumT = transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
//                .filter(t -> t.getYear() < 2012)
                .map(Transaction::getValue)
                .reduce(0, Integer::sum);
        System.out.println(sumT);
        System.out.println("7、所有交易中，最高的交易额是多少");
        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .ifPresent(System.out::println);
        System.out.println("8、交易额最小的交易");
        transactions.stream()
                .reduce((x, y) -> x.getValue() > y.getValue() ? y : x)
                .ifPresent(t -> System.out.println(t.toString()));
//        transactions.stream()
//                .min(Comparator.comparing(Transaction::getValue))
//                .ifPresent(t -> System.out.println(t.toString()));
        // 特化成数值流
        transactions.stream()
                .mapToInt(Transaction::getValue)
                .max()//min average
                .ifPresent(System.out::println);
        System.out.println("9、按照年份分组统计");
        transactions.stream().collect(Collectors.groupingBy(Transaction::getYear))
                .entrySet()
                .stream()
                .forEach(e -> System.out.println(e.getKey() + "--" + e.getValue()));



        String[] idsStr = "8,6,9,5".split(",");
        Arrays.stream(idsStr)
                .mapToLong(id->Long.valueOf(id))
                .forEach(System.out::println);
    }

    @Test
    public void testStream() {
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