package com.tequeno.net.bio;

import com.tequeno.net.InetConst;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 一个文件的传输过程
 * 简单的从客户端磁盘读取然后通过socket上传到服务器的磁盘上
 */
public class BioFileTransferModel {

    public void server(String fileName) {
        try (ServerSocket serverSocket = new ServerSocket(InetConst.BIO_SERVER_PORT);
             Socket socket = serverSocket.accept();
             InputStream inputStream = socket.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] bytes = new byte[1024];
            int read, total = 0;
            long l1 = System.currentTimeMillis();
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
                total += read;
            }
            long l2 = System.currentTimeMillis();
            System.out.println("接收" + total + "字节,耗时" + (l2 - l1) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void client(String fileName) {
        try (Socket socket = new Socket(InetConst.HOSTNAME, InetConst.BIO_SERVER_PORT);
             InputStream inputStream = new FileInputStream(fileName);
             OutputStream outputStream = socket.getOutputStream()) {
            byte[] bytes = new byte[1024];
            int read, total = 0;
            long l1 = System.currentTimeMillis();
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
                total += read;
            }
            long l2 = System.currentTimeMillis();
            System.out.println("传输" + total + "字节,耗时" + (l2 - l1) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
