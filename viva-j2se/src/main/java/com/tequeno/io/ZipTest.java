package com.tequeno.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipTest {

    public static void toZip(List<File> srcFiles, String zipPath) {
        byte[] buf = new byte[1024];
        long start = System.currentTimeMillis();
        try (OutputStream out = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(out)) {
            for (File srcFile : srcFiles) {
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                InputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            System.out.println("压缩异常");
        }
        long end = System.currentTimeMillis();
        System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        System.out.println(buf.length);
    }

    public static void main(String[] args) throws Exception {

        List<File> fileList = new ArrayList<>();
        fileList.add(new File("/data/pic/5639395138950405.jpg"));
        fileList.add(new File("/data/pic/6668538023345750.jpg"));
        fileList.add(new File("/data/pic/16575137789103803.jpg"));
        ZipTest.toZip(fileList, "/data/pic/pink_floyd2.zip");
    }
}