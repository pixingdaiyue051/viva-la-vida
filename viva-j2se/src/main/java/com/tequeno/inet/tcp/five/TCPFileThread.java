package com.tequeno.inet.tcp.five;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

public class TCPFileThread implements Runnable {

	private Socket socket;

	public TCPFileThread() {
	}

	public TCPFileThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		String ip = socket.getInetAddress().getHostAddress();
		try (// 输入流获取为文件名
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				// 输入流获取文件信息(二进制流)
				BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
				FileOutputStream fos = new FileOutputStream(this.checkFileInServer(dis.readUTF()));) {

			byte[] b = new byte[1024];

			while (bis.read(b) > 0) {
				fos.write(b);
				fos.flush();
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(ip + "上传失败");
		}
	}

	private File checkFileInServer(String fileName) throws Exception {
		int index = fileName.lastIndexOf(".");
		String preFix = fileName.substring(0, index);
		File file = new File("E:\\Files\\hexk\\doc\\test2");
		if (!file.isDirectory()) {
			file.mkdirs();
		}
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
