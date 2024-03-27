package com.tequeno.file;

import java.io.*;
import java.util.Random;

/**
 * 模拟读取一个10G文件
 * 现有一个 10G 文件的数据，里面包含了 18-70 之间的整数，分别表示 18-70 岁的人群数量统计。假设年龄范围分布均匀，分别表示系统中所有用户的年龄数，
 * 找出重复次数最多的那个数，现有一台内存为 4G、2 核 CPU 的电脑
 * <p>
 */
public class BigfileWriter {

    public File prepareFile(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 产生10G的数据
     */
    public void generateData(File file) {
        Random random = new Random();
        int start = 18;
        int end = 70;
        try (FileOutputStream fos = new FileOutputStream(file, true);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bos = new BufferedWriter(osw)) {

            long l1 = System.currentTimeMillis();
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                String data = (random.nextInt(end - start + 1) + start) + ",";
                bos.write(data);
                // 每1000条记录成一行
                if (i % 1000 == 0) {
                    bos.write("00,"); // 加入一个和数据位大小一致的数据换行
                    bos.newLine(); // 写入一行大约4kb大小
                    System.out.println("写入1000条数据,耗时:" + (System.currentTimeMillis() - l1) + "ms");
                }
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}