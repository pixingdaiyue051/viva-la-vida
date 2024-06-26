package com.tequeno.file;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FileHandler {

    public void recursiveListFile() {
        try(final FileChannel fout = FileChannel.open(Paths.get("E:\\data\\亲视通后台操作系统软著申请\\亲视通后台操作系统代码.docx"),
                StandardOpenOption.CREATE,StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            File f = new File("E:\\hexk\\workstattion\\ningBo-admin\\src");
            recursiveListFile(f, 0, fout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recursiveListFile(File file, int len, FileChannel fout) {
        IntStream.range(0, len).forEach(i -> System.out.print("\t"));
        System.out.println(file.getName());
        // 处理文件
        handleFile(file.getAbsolutePath(), fout);
        if (file.isDirectory()) {
            File[] files = file.listFiles(f -> !f.isHidden() && !f.getName().startsWith("."));
            int finalLen = ++len;
            Stream.of(files).forEach(f -> recursiveListFile(f, finalLen, fout));
        }
    }

    private void handleFile(String path, FileChannel fout) {
        try (final FileChannel fin = FileChannel.open(Paths.get(path), StandardOpenOption.READ)) {
            long read, total = 0;
            while ((read = fin.transferTo(total, fin.size(), fout)) > 0) {
                total += read;
            }
        } catch (Exception e) {

        }
    }

    public void readFile() {
        try (//InputStream is = new FileInputStream("/data/doc/___user1.dat");
             final FileChannel fc = FileChannel.open(Paths.get("/data/doc/___user1.dat"), StandardOpenOption.READ)) {

//            final byte[] bytes = is.readAllBytes();
//            System.out.println(new String(bytes, StandardCharsets.UTF_8));

            final int length = (int) fc.size();

            ByteBuffer buffer = ByteBuffer.allocate(length);
            final int read = fc.read(buffer, 24);
            System.out.println(read);
            System.out.println(new String(buffer.array(), StandardCharsets.UTF_8));
            System.out.println("===========================================");
            // 读写翻转
            buffer.flip();
            // 读取有效文件
            final byte[] bytes = new byte[length - 26];
            buffer.get(bytes, 0, length - 26);
            final String x = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(x.length());
            System.out.println(x);
            System.out.println("===========================================");
            // 解码
//            final Base64.Decoder decoder = Base64.getDecoder();
//            final byte[] decoded = decoder.decode(bytes);
            final byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(bytes);
            System.out.println(new String(decoded, StandardCharsets.UTF_8));


            // 清不清都所谓
            buffer.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
