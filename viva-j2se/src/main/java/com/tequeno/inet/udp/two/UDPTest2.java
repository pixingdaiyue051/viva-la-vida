package com.tequeno.inet.udp.two;

import java.net.DatagramSocket;

import com.tequeno.inet.InetConst;

public class UDPTest2 {

	public static void main(String[] args) {
		try {
			DatagramSocket dss = new DatagramSocket(InetConst.SENDPORT);
			DatagramSocket dsr = new DatagramSocket(InetConst.RECIEVEPORT);
			UDPSendHandler sendHandler = new UDPSendHandler(dss);
			UDPRecieveHandler recieveHandler = new UDPRecieveHandler(dsr);
			Thread ts = new Thread(sendHandler);
			Thread tr = new Thread(recieveHandler);
			ts.start();
			tr.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
