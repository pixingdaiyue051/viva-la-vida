package com.tequeno.inet.tcp.two;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.tequeno.inet.InetConst;

public class TCPClientHandler implements Runnable{

	@Override
	public void run() {
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
}
