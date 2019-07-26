package com.socket;

import java.net.*;
import java.io.*;

/**
 * 如下的 GreetingClient 是一个客户端程序，该程序通过 socket 连接到服务器并发送一个请求，然后等待一个响应。
 * 
 * @author xinbe
 *
 */
public class GreetingClient {
	public static void main(String[] args) {
		//String serverName = args[0];
		//int port = Integer.parseInt(args[1]);
		//String serverName = "localhost";
		//String serverName = "192.168.2.75";
		String host = "192.168.1.13";
		int port = 5555;
		
		try {
			System.out.println("[客户端提示]===已经连接到服务器,主机:" + host + ", 端口:" + port + "!");
			
			//构造客户端socket
			//Socket client = new Socket(serverName, port);
			Socket client = new Socket(host, port);
			//client.bind(new InetSocketAddress(port));
			
			//Socket client = new Socket();
			//client.bind(new InetSocketAddress(host,port));
			
			//获取连接地址
			System.out.println("[客户端连接服务器]===远程主机地址:" + client.getRemoteSocketAddress());
			
			//创建输出流缓冲区对象DataOutputStream
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			//写入信息
			out.writeUTF("<客户端发送>请求服务:" + client.getLocalSocketAddress());
			
			//创建输入流缓冲区对象DataInputStream
			DataInputStream in = new DataInputStream(client.getInputStream());
			
			try {
				String content = in.readUTF();
				System.out.println("<客户端接收>" + content);
			} catch (EOFException e) {
				System.out.println("客户端正常关闭!");
			}
			
			//读取信息
			
			//关闭socket
			client.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}