package com.tequeno.zerocp;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelModel {

    public void fileTransfer(String src, String dst) {
        try (FileChannel inChannel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);
             FileChannel outChannel = FileChannel.open(Paths.get(dst),
                     StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            long read, total = 0;
            long l1 = System.currentTimeMillis();

            while ((read = inChannel.transferTo(total, inChannel.size() - total, outChannel)) != 0) {
                total += read;
            }

            long l2 = System.currentTimeMillis();
            System.out.println("传输" + total + "字节,耗时" + (l2 - l1) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
