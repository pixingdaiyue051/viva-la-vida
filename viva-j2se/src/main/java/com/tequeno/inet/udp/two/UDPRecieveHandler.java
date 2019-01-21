package com.tequeno.inet.udp.two;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPRecieveHandler implements Runnable {

	private DatagramSocket ds;

	public UDPRecieveHandler(DatagramSocket ds) {
		this.ds = ds;
	}

	@Override
	public void run() {
		// 1.创建UDP服务,制定一个端口,不指定则有系统随机分配
		try {
			while (true) {
				// 2.建立接收数据报文的对象
				byte[] buf = new byte[1024];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				// 3.接收报文 采用传统的bio阻塞线程方式等待发包
				ds.receive(dp);
				// 4.获取数据报文中的信息
				byte[] b = dp.getData();
				String address = dp.getAddress().getHostAddress();
				String data = new String(b, 0, dp.getLength());
				System.out.println(address + ":" + data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
