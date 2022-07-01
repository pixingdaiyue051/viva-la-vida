package com.tequeno.io;

import java.io.*;
import java.util.Random;

public class BigFileWriter {

    private Random random;

    private int start;

    private int end;

    public BigFileWriter() {
        random = new Random();
        start = 18;
        end = 70;
    }

    public int generateRandomData() {
        return random.nextInt(end - start + 1) + start;
    }

    /**
     * 产生10G的数据
     */
    public void generateData(File file) {
        try (FileOutputStream fos = new FileOutputStream(file, true);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bos = new BufferedWriter(osw)) {

            long l1 = System.currentTimeMillis();
            for (long i = 1; i < 100000000; i++) {
                String data = generateRandomData() + ",";
                bos.write(data);
                // 每100万条记录成一行，100万条数据大概4M
                if (i % 1000 == 0) {
                    bos.write("00\n"); // 加入一个和数据位大小一致的数据换行
                    System.out.println("写入1000000数据,耗时:" + (System.currentTimeMillis() - l1) + "ms");
                }
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}