package com.tequeno.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

public class FileHandler {

    public void recursiveListFile(String filePath) {
        recursiveListFile(new File(filePath), 0);
    }

    private void recursiveListFile(File file, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print("\t");
        }
        System.out.println(file.getName());
        if (!file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles(f -> !f.isHidden() && !f.getName().startsWith("."));
        if (null == files || files.length < 1) {
            return;
        }
        for (File value : files) {
            recursiveListFile(value, len + 1);
        }
    }

    public void recursiveListFile(String filePath, String outPath) {
        try (final FileChannel channel = FileChannel.open(Paths.get(outPath),
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            recursiveListFile(new File(filePath), s -> write2File(s, channel));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recursiveListFile(File file, Consumer<String> consumer) {
        if (file.isFile() && !file.getName().endsWith("exe")) {
            consumer.accept(file.getAbsolutePath());
        }
        if (!file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles(f -> !f.isHidden() && !f.getName().startsWith("."));
        if (null == files || files.length < 1) {
            return;
        }
        for (File value : files) {
            recursiveListFile(value, consumer);
        }
    }

    private void write2File(String path, FileChannel channel) {
        try (final FileChannel fin = FileChannel.open(Paths.get(path), StandardOpenOption.READ)) {
            long read, total = 0;
            while ((read = fin.transferTo(total, fin.size(), channel)) > 0) {
                total += read;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void randomAccessFile(String pathIn, String pathOut) {
        try (RandomAccessFile fileIn = new RandomAccessFile(pathIn, "r");
             RandomAccessFile fileOut = new RandomAccessFile(pathOut, "rw")) {

//            String s;
//            while (null != (s = fileIn.readLine())) {
//                System.out.println(s);
//            }

            byte[] b = new byte[1024];
            int len;
            while ((len = fileIn.read(b)) > 0) {
                fileOut.seek(fileOut.length()); // RandomAccessFile不会自动移动文件指针，需要手动设置，每次都移动到文件末尾
                fileOut.write(b,0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
