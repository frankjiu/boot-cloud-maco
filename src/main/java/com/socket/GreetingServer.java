package com.socket;

import java.net.*;
import java.io.*;

/**
 * 服务端
 * @author xinbe
 *
 */
public class GreetingServer extends Thread {
	private ServerSocket serverSocket;

	//构造服务器socket
	public GreetingServer(int port) throws IOException {
		//根据端口号创建服务器socket
		serverSocket = new ServerSocket(port);
		//设置连接超时时间10s
		serverSocket.setSoTimeout(10000);
	}

	public void run() {
		while (true) {
			try {
				//获取监听的端口号
				System.out.println("[服务器提示]===监听端口号为:" + serverSocket.getLocalPort() + ", 等待客户端连接......");
				
				//监听并接受套接字连接(在此之前属于等待连接状态)
				Socket server = serverSocket.accept();
				
				//连接的端点地址
				System.out.println("[服务器连接客户端]===远程主机地址:" + server.getRemoteSocketAddress());
				
				//创建输入流缓冲区对象DataInputStream
				DataInputStream in = new DataInputStream(server.getInputStream());
				//读取信息
				System.out.println("<服务器接收>" + in.readUTF());
				
				//创建输出流缓冲区对象DataOutputStream
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				//写入信息
				out.writeUTF("<服务器响应>谢谢连接我:" + server.getLocalSocketAddress() + "\nGoodbye!");
				
				//关闭socket
				server.close();
				
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	//测试服务器socket
	public static void main(String[] args) {
		//int port = Integer.parseInt(args[0]);
		int port = 5555;
		try {
			Thread t = new GreetingServer(port);
			t.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}