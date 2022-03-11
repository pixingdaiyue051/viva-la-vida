package com.tequeno.inet.bio;

import com.tequeno.inet.InetConst;

import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

public class BioFileTransferServer {

    public static void main(String[] args) {
        BioFileTransferModel model = new BioFileTransferModel();
        model.server("/data/doc/1.mp4");
    }

    private void fileTransfer() {
        File file = new File("/data/doc/2.txt");
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw");
             Socket socket = new Socket(InetConst.HOSTNAME, InetConst.BIO_SERVER_PORT);
             OutputStream outputStream = socket.getOutputStream()) {
            // 读取文件内容
            byte[] bytes = new byte[(int) file.length()];
            raf.read(bytes);
            // 写入到socket中
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
