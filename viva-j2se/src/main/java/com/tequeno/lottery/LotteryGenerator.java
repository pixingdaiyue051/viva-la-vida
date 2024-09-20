package com.tequeno.lottery;

import java.util.*;

public class LotteryGenerator {

    private final int maxRedNum;

    private final int maxBlueNum;

    private final Random r;
    private final int bets; // 彩票注数

    public LotteryGenerator() {
        this(5);
    }

    public LotteryGenerator(int bets) {
        this.bets = bets;
        this.r = new Random();
        this.maxRedNum = 33;
        this.maxBlueNum = 16;
    }

    public List<Lottery> generateDefault() {
        Set<String> set1 = Set.of("01", "05", "07", "11", "15", "26");
        Set<String> set2 = Set.of("01", "03", "04", "07", "26", "31");
        Set<String> set3 = Set.of("06", "07", "14", "20", "27", "28");
        Set<String> set4 = Set.of("08", "10", "14", "24", "25", "31");
        Set<String> set5 = Set.of("05", "06", "16", "17", "29", "30");
        final String blue = "03";
        return List.of(new Lottery(set1, blue), new Lottery(set2, blue), new Lottery(set3, blue), new Lottery(set4, blue), new Lottery(set5, blue));
    }

    public List<Lottery> generate() {
        List<Lottery> list = new ArrayList<>(bets);
        for (int i = 0; i < bets; i++) {
            list.add(this.generateOne());
        }
        return list;
    }

    public Lottery generateOne(String line) {
        String[] strings = line.split(",");
        Set<String> redSet = new HashSet<>(Arrays.asList(strings).subList(0, strings.length - 1));
        return new Lottery(redSet, strings[strings.length - 1]);
    }

    public Lottery generateOne() {
        Set<String> redSet = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            fillRed(redSet);
        }
        int blueNum = r.nextInt(maxBlueNum) + 1;
        String blue = String.format("%02d", blueNum);
        return new Lottery(redSet, blue);
    }

    private void fillRed(Set<String> redSet) {
        int redNum = r.nextInt(maxRedNum) + 1;
        String red = String.format("%02d", redNum);
        if (!redSet.add(red)) {
            fillRed(redSet);
        }
    }
}
