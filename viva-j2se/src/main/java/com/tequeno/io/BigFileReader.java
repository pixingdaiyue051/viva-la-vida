package com.tequeno.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class BigFileReader {

    private final Map<String, AtomicInteger> countMap;
    private final CompletionService<String> service;
    private final ExecutorService pool;
    private final long eachLength;

    public BigFileReader() {
        pool = Executors.newCachedThreadPool();
        service = new ExecutorCompletionService<>(pool);
        countMap = new ConcurrentHashMap<>(128);
        eachLength = 40960;
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
    public void singRead(File file) {
        long l1 = System.currentTimeMillis();
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {


            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                // 按行读取
                splitLine(line);
                if (count % 100 == 0) {
                    System.out.println("读取100行,耗时: " + (System.currentTimeMillis() - l1) + "ms");
                }
                count++;
            }
            findMaxAge();
            System.out.println("读取处理完毕,共耗时: " + (System.currentTimeMillis() - l1) + "ms");
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
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
            int count = 0;

            while ((line = br.readLine()) != null) {
                // 按行读取
                String finalLine = line;
                service.submit(() -> {
                    splitLine(finalLine);
                    return "";
                });
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
     * @param filepath
     */
    public void threadReadPlus(String filepath) {
        long l1 = System.currentTimeMillis();
        try (final FileChannel fc = FileChannel.open(Paths.get(filepath), StandardOpenOption.READ)) {
            final long fileSize = fc.size();
            System.out.println("总字节数" + fileSize);
            long threads = (fileSize / eachLength) + 1;
            for (int i = 0; i < threads; i++) {
                final int finalI = i;
                service.submit(() -> {
                    final ByteBuffer allocate = ByteBuffer.allocate((int) eachLength);
                    fc.read(allocate, finalI * eachLength);
                    final String str = new String(allocate.array(), StandardCharsets.UTF_8);
                    splitLine(str);
                    System.out.println("读取" + eachLength + "bytes,耗时: " + (System.currentTimeMillis() - l1) + "ms");
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
            System.out.println("读取处理完毕,共耗时: " + (System.currentTimeMillis() - l1) + "ms");
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
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