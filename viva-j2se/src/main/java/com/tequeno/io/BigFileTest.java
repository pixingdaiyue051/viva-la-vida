package com.tequeno.io;

import java.io.File;
import java.io.IOException;

/**
 * 模拟读取一个10G文件
 * 现有一个 10G 文件的数据，里面包含了 18-70 之间的整数，分别表示 18-70 岁的人群数量统计。假设年龄范围分布均匀，分别表示系统中所有用户的年龄数，
 * 找出重复次数最多的那个数，现有一台内存为 4G、2 核 CPU 的电脑
 * <p>
 */
public class BigFileTest {

    private static String filepath = "/data/upload/bigdata_test/test.dat";

    private static File prepareFile(String filepath) {
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

    public static void main(String[] args) {
        final File file = prepareFile(filepath);

//        BigFileWriter writer = new BigFileWriter();
//        writer.generateData(file);

//        BigFileReader reader = new BigFileReader();
//        reader.readData(file);

        BigFileConcurrentReader reader = new BigFileConcurrentReader();
        reader.readData(file);

//        BigFileCoReader reader = new BigFileCoReader();
//        reader.readData(filepath);

    }

}