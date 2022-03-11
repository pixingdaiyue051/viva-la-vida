package com.tequeno.inet.tcp.three;

import com.tequeno.inet.InetConst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * TCP分客户端和服务端 Socket和ServerSockt 建立套接字时需要指明主机和端口
 */
public class TCPTransHandler {

    /**
     * 建立socket客户端同时会创建输入输出流 使用输出流向服务端发送数据 使用输入流获取服务端传输的数据
     */
    public void doClient() {
        try (Socket socket = new Socket(InetConst.HOSTNAME, InetConst.SENDER_PORT);
             // 1.获得键盘输入流
             Scanner scan = new Scanner(System.in);
             // 2.获得socket输出流，向服务端发送数据
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             // 3.获得socket输入流，读取服务端返回的数据
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String serverIp = socket.getInetAddress().getHostAddress();
            String line = null;
            while (true) {
                line = scan.nextLine();
                if (InetConst.BREAK_OUT.equalsIgnoreCase(line)) {
                    break;
                } else {
                    // 发送数据
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                    // 接收数据
                    System.out.println("服务端" + serverIp + "返回...." + br.readLine());
                }
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
        try (ServerSocket serverSocket = new ServerSocket(InetConst.SENDER_PORT);
             Socket socket = serverSocket.accept();
             // 1.获得socket输入流，读取客户端发送的数据
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             // 2.获得socket输出流，向客户端发送数据
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {

            String clientIp = socket.getInetAddress().getHostAddress();
            String line = null;
            while ((line = br.readLine()) != null) {
                // 读取客户端的数据
                System.out.println("客户端" + clientIp + "发来...." + line);
                // 处理数据并返回
                bw.write(line.toUpperCase());
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
