package com.tequeno.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipHandler {

    public void toZip(List<File> srcFiles, String zipPath) {
        byte[] buf = new byte[1024];
        long start = System.currentTimeMillis();
        try (OutputStream out = Files.newOutputStream(Paths.get(zipPath));
             ZipOutputStream zos = new ZipOutputStream(out)) {
            for (File srcFile : srcFiles) {
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                InputStream in = Files.newInputStream(srcFile.toPath(), StandardOpenOption.READ);
                while ((len = in.read(buf)) > 0) {
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

}