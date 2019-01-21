package com.tequeno.inet.tcp.one;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.tequeno.inet.InetConst;

/**
 * TCP分客户端和服务端 Socket和ServerSockt 建立套接字时需要指明主机和端口
 */
public class TCPHandler {

	/**
	 * 建立socket客户端同时会创建输入输出流 使用输出流向服务端发送数据 使用输入流获取服务端传输的数据
	 */
	public void doClient() {
		try (Socket socket = new Socket(InetConst.HOSTNAME, InetConst.SENDPORT);) {
			// 1.向服务端发送数据
			OutputStream out = socket.getOutputStream();
			out.write("let me take care of you".getBytes());
			// 2.获取服务端回传的数据
			String serverIp = socket.getInetAddress().getHostAddress();
			InputStream in = socket.getInputStream();
			byte[] b = new byte[1024];
			int len = in.read(b);
			String data = new String(b, 0, len, InetConst.UTF8);
			System.out.println("服务端" + serverIp + "发回消息:" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立服务端,绑定一个端口,等待客户端建立连接 一旦建立连接就可获取客户端socket及输入输出流 使用对应客户端的输入流获取该客户端发送的数据
	 * 使用输出流向回传数据 服务端socket因为要和多个客户端多次通信,所以可以不用关闭
	 */
	public void doServer() {
		try (ServerSocket serverSocket = new ServerSocket(InetConst.SENDPORT); Socket socket = serverSocket.accept();) {
			// 1.获取客服端的发送来的信息
			// accept方法是阻塞式获取socket流,没有则一直等待直到有客户端连接上
			String clientIp = socket.getInetAddress().getHostAddress();
			InputStream in = socket.getInputStream();
			byte[] b = new byte[1024];
			int len = in.read(b);
			String data = new String(b, 0, len, InetConst.UTF8);
			System.out.println("客户端" + clientIp + "发来消息:" + data);
			// 2.向客户端回传数据
			Thread.sleep(5000L);
			OutputStream out = socket.getOutputStream();
			data = "silly boy,keep yourself";
			out.write(data.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
