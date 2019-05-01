package com.jdk8.cstream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TraderTest {
    public static void main(String[] args) {
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

    }
}
