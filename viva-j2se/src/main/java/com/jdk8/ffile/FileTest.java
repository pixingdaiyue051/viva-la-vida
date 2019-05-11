package com.jdk8.ffile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileTest {
    public static void main(String[] args) {

        // nio生产文件流
        long uniqueWords = 0;
        try (Stream<String> lines =
                     Files.lines(Paths.get(".", "doc/data.txt").normalize(), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split("")))
//                    .distinct()
//                    .filter(l -> !l.matches(" "))
                    .count();// 统计文件字符数
//            uniqueWords = lines.count();// 行数
            System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(Paths.get(".", "doc/data.txt").normalize().toString());
        System.out.println(f.length());
        f = new File(Paths.get("../", "viva-la-vida").normalize().toString());
        recrusiveListFiles(f, 0);
    }

    public static void recrusiveListFiles(File file, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print("\t");
        }
        System.out.println(file.getName());
        if (file.isDirectory()) {
            File[] files = file.listFiles(f -> !f.isHidden() && !f.getName().startsWith("."));
            len++;
            for (File f : files) {
                recrusiveListFiles(f, len);
            }
        }
    }
}
