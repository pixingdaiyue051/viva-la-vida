package com.tequeno.inet.udp.one;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import com.tequeno.inet.InetConst;

public class UDPHandler {

	public void doSend() {
		// 1.创建UDP服务,指明发送端的端口,不声明,系统会默认分配
		try (DatagramSocket ds = new DatagramSocket(InetConst.SENDPORT); Scanner scanner = new Scanner(System.in);) {
			// 2.建立发送数据报文
			String data = null;
			InetAddress i = InetAddress.getByName(InetConst.HOSTNAME);
			while (true) {
				data = scanner.nextLine();
				if ("stop".equalsIgnoreCase(data)) {
					break;
				}
				byte[] buf = data.getBytes();
				DatagramPacket dp = new DatagramPacket(buf, buf.length, i, InetConst.RECIEVEPORT);
				// 3.发送报文
				ds.send(dp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doRecieve() {
		// 1.创建UDP服务,制定一个端口,不指定则有系统随机分配
		try (DatagramSocket ds = new DatagramSocket(InetConst.RECIEVEPORT);) {
			while (true) {
				// 2.建立接收数据报文的对象
				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				// 3.接收报文 采用传统的bio阻塞线程方式等待发包
				ds.receive(dp);
				// 4.获取数据报文中的信息
				byte[] b = dp.getData();
				String address = dp.getAddress().getHostAddress();
				String data = new String(b, 0, dp.getLength(), InetConst.UTF8);
				System.out.println(address + ":" + data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
