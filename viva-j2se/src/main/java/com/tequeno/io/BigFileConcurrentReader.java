package com.tequeno.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程 读取 分析数据
 * <p>
 * 1.从文件中按行读取 -----主线程
 * 2.每读一行就开始统计各年龄的数量 汇总成countMap -----子线程
 * 3.对countMap分析得到数量最多的年龄 -----主线程
 */
public class BigFileConcurrentReader {

    private final Map<String, AtomicInteger> countMap;
    private final CompletionService<String> service;
    private final ExecutorService pool;

    public BigFileConcurrentReader() {
        pool = Executors.newCachedThreadPool();
        service = new ExecutorCompletionService<>(pool);
        countMap = new ConcurrentHashMap<>(128);
    }

    public void readData(File file) {
        long l1 = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {
                // 按行读取
                splitLine(line);
                if (count % 100 == 0) {
                    System.out.println("读取100行,耗时: " + (System.currentTimeMillis() - l1) + "ms");
                }
                count++;
            }
            // 先暂停线程池 不再接收新的任务
            pool.shutdown();
            // 阻塞等到所有子线程都处理完
            for (int i = 0; i < count; i++) {
                service.take().get();
            }
            // 回到主线程分析数据
            findMaxAge();
            System.out.println("读取处理完毕,共耗时: " + (System.currentTimeMillis() - l1) + "ms");
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void splitLine(String lineData) {
        service.submit(() -> {
            String[] arr = lineData.split(",");
            for (String str : arr) {
                countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).incrementAndGet();
            }
            return "";
        });
    }

    private void findMaxAge() {
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