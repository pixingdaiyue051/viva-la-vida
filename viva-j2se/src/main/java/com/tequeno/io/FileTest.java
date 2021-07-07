package com.tequeno.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FileTest {
    public static void main(String[] args) {
//        File file = new File("D:\\hexk\\测试数据文档\\全员类.pdf");
//        String name = file.getName();
//        System.out.println(name);
//        long freeSpace = file.getFreeSpace();
//        System.out.println(freeSpace);
//        long length = file.length();
//        System.out.println(length);

//        // nio生产文件流
//        long uniqueWords = 0;
//        try (Stream<String> lines =
//                     Files.lines(Paths.get("doc/data.txt").normalize(), Charset.defaultCharset())) {
//            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split("")))
////                    .distinct()
////                    .filter(l -> !l.matches(" "))
//                    .count();// 统计文件字符数
////            uniqueWords = lines.count();// 行数
//            System.out.println(uniqueWords);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        FileTest fileTest = new FileTest();
//        fileTest.recursiveListFile(Paths.get("doc").normalize().toString());
//        fileTest.recursiveListFile(Paths.get("../viva-la-vida").normalize().toString());

    }

    private void recursiveListFile(String filePath) {
        File f = new File(filePath);
        recursiveListFile(f, 0);
    }

    private void recursiveListFile(File file, int len) {
        IntStream.range(0, len).forEach(i -> System.out.print("\t"));
        System.out.println(file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles(f -> !f.isHidden() && !f.getName().startsWith("."));
            len++;
            int finalLen = len;
            Stream.of(files).forEach(f -> recursiveListFile(f, finalLen));
        }
    }
}
