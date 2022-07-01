package com.tequeno.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 10G 的数据比当前拥有的运行内存大的多，不能全量加载到内存中读取。如果采用全量加载，那么内存会直接爆掉，只能按行读取。
 * Java 中的 bufferedReader 的 readLine() 按行读取文件里的内容
 * <p>
 * 单线程 读取 分析数据
 * 1.从文件中按行读取
 * 2.每读一行就开始统计各年龄的数量 汇总成countMap
 * 3.对countMap分析得到数量最多的年龄
 */
public class BigFileReader {

    public void readData(File file) {
        long l1 = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {


            String line;
            int count = 1;
            Map<String, AtomicInteger> countMap = new HashMap<>(128);
            while ((line = br.readLine()) != null) {
                // 按行读取
                splitLine(line, countMap);
                if (count % 100 == 0) {
                    System.out.println("读取100行,耗时: " + (System.currentTimeMillis() - l1) + "ms");
                }
                count++;
            }
            findMaxAge(countMap);
            System.out.println("读取处理完毕,共耗时: " + (System.currentTimeMillis() - l1) + "ms");
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void splitLine(String lineData, Map<String, AtomicInteger> countMap) {
        String[] arr = lineData.split(",");
        for (String str : arr) {
            countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).incrementAndGet();
        }
    }

    private void findMaxAge(Map<String, AtomicInteger> countMap) {
        long l1 = System.currentTimeMillis();
        Optional<Map.Entry<String, AtomicInteger>> maxEntry = countMap.entrySet().stream()
                .max(Comparator.comparingInt(v -> v.getValue().get()));
        maxEntry.ifPresent(e -> System.out.println("数量最多的年龄为:" + e.getKey() + "数量为：" + e.getValue().get()));
        System.out.println("处理完毕,耗时: " + (System.currentTimeMillis() - l1) + "ms");

        long l2 = System.currentTimeMillis();
        int targetValue = 0;
        String targetKey = null;
        for (Map.Entry<String, AtomicInteger> entry : countMap.entrySet()) {
            int value = entry.getValue().get();
            String key = entry.getKey();
            if (value > targetValue) {
                targetValue = value;
                targetKey = key;
            }
        }
        System.out.println("数量最多的年龄为:" + targetKey + "数量为：" + targetValue);
        System.out.println("处理完毕,耗时: " + (System.currentTimeMillis() - l2) + "ms");
    }
}