package com.tequeno.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class ZipHandler {

    public void toZip(List<File> srcFiles, String zipPath) {
        long start = System.currentTimeMillis();

        try (OutputStream out = Files.newOutputStream(Paths.get(zipPath));
             ZipOutputStream zos = new ZipOutputStream(out)) {

            byte[] buf = new byte[1024];
            int len;

            for (File srcFile : srcFiles) {
                String fileName = srcFile.getName();
                zos.putNextEntry(new ZipEntry(fileName));
                try (InputStream in = Files.newInputStream(srcFile.toPath(), StandardOpenOption.READ)) {
                    while ((len = in.read(buf)) > 0) {
                        zos.write(buf, 0, len);
                    }
                    zos.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(fileName + "压缩失败");
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("压缩异常");
        }
    }

    public void unzip(String zipFilePath, String desDir) {
        long start = System.currentTimeMillis();

        try (InputStream fis = Files.newInputStream(Paths.get(zipFilePath), StandardOpenOption.READ);
             ZipInputStream zis = new ZipInputStream(fis)) {

            byte[] buffer = new byte[1024];
            int length;

            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                String fileName = ze.getName();
                try (OutputStream os = Files.newOutputStream(Paths.get(desDir, File.separator, fileName))) {
                    while ((length = zis.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                    zis.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(fileName + "解压失败");
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("解压异常");
        }
    }

}