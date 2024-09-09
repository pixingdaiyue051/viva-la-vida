package com.tequeno.net.tcp;

import com.tequeno.net.InetConst;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过socket向服务器发送文件
 * TCP分客户端Socket和服务端ServerSocket
 */
public class TCPFileSocketHandler {

    private final static String PATH = "/data/upload";

    /**
     * 建立socket客户端同时会创建输入输出流 使用输出流向服务端发送数据 使用输入流获取服务端传输的数据
     */
    public void doClient(String path) {
        File file = new File(path);
        try (Socket socket = new Socket(InetConst.HOSTNAME, InetConst.SERVER_PORT);
             FileInputStream fis = new FileInputStream(file);
             // 输出流传输文件名
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             // 输出流传输文件信息(二进制流)
             BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {
            dos.writeUTF(file.getName());
            byte[] b = new byte[1024];
            while (fis.read(b) > 0) {
                bos.write(b);
                bos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立服务端,绑定一个端口,等待客户端建立连接 一旦建立连接就可获取客户端socket及输入输出流 使用对应客户端的输入流获取该客户端发送的数据
     * 使用输出流向回传数据 服务端socket因为要和多个客户端多次通信,所以可以不用关闭
     */
    public void doServer() {
        File file = new File(PATH);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        try (ServerSocket serverSocket = new ServerSocket(InetConst.SERVER_PORT);
             Socket socket = serverSocket.accept();
             // 输入流获取为文件名
             DataInputStream dis = new DataInputStream(socket.getInputStream());
             // 输入流获取文件信息(二进制流)
             BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
             FileOutputStream fos = new FileOutputStream(this.checkFileInServer(dis.readUTF(), file))) {

            byte[] b = new byte[1024];

            while (bis.read(b) > 0) {
                fos.write(b);
                fos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File checkFileInServer(String fileName, File file) {
        int index = fileName.lastIndexOf(".");
        String preFix = fileName.substring(0, index);
        int i = 0;
        for (File f : file.listFiles()) {
            if (f.getAbsolutePath().contains(preFix)) {
                i++;
            }
        }
        if (i > 0) {
            String subFix = fileName.substring(index);
            fileName = preFix + "(" + i + ")" + subFix;
        }
        file = new File(file.getAbsolutePath() + "\\" + fileName);
        return file;
    }
}
