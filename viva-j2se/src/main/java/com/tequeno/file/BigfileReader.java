package com.tequeno.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class BigfileReader {

    private final Map<String, AtomicInteger> countMap;
    private final CompletionService<String> service;
    private final ExecutorService pool;
    private final int eachLength;

    public BigfileReader() {
        pool = Executors.newCachedThreadPool();
        service = new ExecutorCompletionService<>(pool);
        countMap = new ConcurrentHashMap<>(128);
        eachLength = 40960; // 大约10行数据
    }

    /**
     * 10G 的数据比当前拥有的运行内存大的多，不能全量加载到内存中读取。如果采用全量加载，那么内存会直接爆掉，只能按行读取。
     * Java 中的 bufferedReader 的 readLine() 按行读取文件里的内容
     * <p>
     * 单线程 读取 分析数据
     * 1.从文件中按行读取
     * 2.每读一行就开始统计各年龄的数量 汇总成countMap
     * 3.对countMap分析得到数量最多的年龄
     *
     * @param file
     */
    public void singleRead(File file) {
        long l1 = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                // 按行读取
                splitLine(line);
                if (count % 1000 == 0) {
                    System.out.println("读取1000行,耗时:" + (System.currentTimeMillis() - l1) + "ms");
                }
                count++;
            }
            findMaxAge();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
    }

    /**
     * 多线程 读取 分析数据
     * <p>
     * 1.从文件中按行读取 -----主线程
     * 2.每读一行就开始统计各年龄的数量 汇总成countMap -----子线程
     * 3.对countMap分析得到数量最多的年龄 -----主线程
     *
     * @param file
     */
    public void threadRead(File file) {
        long l1 = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                // 按行读取
                String finalLine = line;
                service.submit(() -> {
                    splitLine(finalLine);
                    return "";
                });
                if (count % 1000 == 0) {
                    System.out.println("读取1000行,耗时:" + (System.currentTimeMillis() - l1) + "ms");
                }
                count++;
            }
            // 先暂停线程池 不再接收新的任务
            pool.shutdown();
            // 阻塞等到所有子线程都处理完
            for (int i = 1; i < count; i++) {
                service.take().get();
            }
            // 回到主线程分析数据
            findMaxAge();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
    }

    /**
     * 多线程 读取 分析数据
     * <p>
     * 1.获得文件channel分析文件大小分配使用的线程数 -----主线程
     * 2.多线程同时按字节位移读取文件 统计各年龄的数量汇总成countMap -----子线程
     * 3.对countMap分析得到数量最多的年龄 -----主线程
     * <p>
     * 因为文件大小的不可控 当文件超出系统内存或程序申请内存时
     * 多线程同时读取文件有可能会导致oom
     * 主线读文件效率本身并不低 效率瓶颈就在内存分配和cpu抢占上
     *
     * @param file
     */
    public void threadReadPlus(File file) {
        long l1 = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            long size = fis.getChannel().size();
            long threads = (size / eachLength) + 1;
            for (int i = 0; i < threads; i++) {
                int finalI = i;
                service.submit(() -> {
                    char[] b = new char[eachLength];
                    int read = br.read(b, finalI * eachLength, eachLength);
                    splitLine(Arrays.toString(b));
                    System.out.println("读取" + read + "bits,耗时:" + (System.currentTimeMillis() - l1) + "ms");
                    return "";
                });
            }
            // 先暂停线程池 不再接收新的任务
            pool.shutdown();
            // 阻塞等到所有子线程都处理完
            for (int i = 0; i < threads; i++) {
                service.take().get();
            }
            // 回到主线程分析数据
            findMaxAge();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
    }

    private void splitLine(String lineData) {
        String[] arr = lineData.split(",");
        for (String str : arr) {
            countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).incrementAndGet();
        }
    }

    private void findMaxAge() {
        long l1 = System.currentTimeMillis();
        Optional<Map.Entry<String, AtomicInteger>> maxEntry = countMap.entrySet().stream()
                .max(Comparator.comparingInt(v -> v.getValue().get()));
        maxEntry.ifPresent(e -> System.out.println("数量最多的年龄为:" + e.getKey() + "数量为：" + e.getValue().get()));
        System.out.println("处理完毕,耗时:" + (System.currentTimeMillis() - l1) + "ms");

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
        System.out.println("数量最多的年龄为:" + targetKey + "数量为:" + targetValue);
        System.out.println("处理完毕,耗时:" + (System.currentTimeMillis() - l2) + "ms");
    }
}