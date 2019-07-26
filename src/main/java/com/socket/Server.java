package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set; //聊天室服务端

public class Server {
	// 运行在服务端的ServerSocket主要负责：
	// 1.向系统申请服务端口, 客户端就是通过这个端口与之建立连接的
	// 2.监听申请的服务端口, 当一个客户端通过该端口尝试建立连接时,ServerSocket会在服务端创建一个Socket与客户端建立连接.
	private ServerSocket server;// 用来初始化服务端
	private Map<String, PrintWriter> allOut;// 保存所有客户端输出流的集合

	// 初始化的同时申请服务端口
	public Server() throws IOException {
		server = new ServerSocket(5555);
		allOut = new HashMap<String, PrintWriter>();
	}
	
	/**
	 *  入口
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("服务端启动失败");
		}
	}
	
	// 将给定的输出流添加到共享集合
	private synchronized void addToOut(String nickName, PrintWriter out) {
		allOut.put(nickName, out);
	}
	
	// 将给定的输出流从共享集合中删除
	private synchronized void removeOut(String nickName) {
		allOut.remove(nickName);
	}
	
	// 将给定的消息发送给所有客户端
	private void sendMessage(String message) {
		Set<Map.Entry<String, PrintWriter>> entryset = allOut.entrySet();
		for (Map.Entry<String, PrintWriter> e : entryset) {
			e.getValue().println(message);
		}
	}
	
	// 将给定消息发送给特定上线用户
	private void sendOneMessage(String whoName, String message) {
		String keyName = message.substring(1, message.indexOf(":"));
		PrintWriter out = allOut.get(keyName);
		System.out.println("out is " + out + keyName);
		if (out != null) {
			out.println(whoName + "对你说：" + message.substring(message.indexOf(":")));
		} else {
			System.out.println("发送失败");
		}
	}
	
	// 服务端开始工作的方法
	public void start() {
		try {
			// ServerSocket的accept方法,是一个堵塞方法,作用是监听服务端口,直到一个客户端连接并创建一个Socket,使用该Socket即可与刚连接的客户端进行交互
			System.out.println("等待客户端连接····");
			while (true) {
				Socket socket = server.accept();
				System.out.println("一个客户端连接了");
				ClientHandler handler = new ClientHandler(socket);
				Thread t = new Thread(handler);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 该线程处理客户端的Socket
	class ClientHandler implements Runnable {
		private Socket socket;
		private String nickName;
		private String host;// 客户端的地址信息

		public ClientHandler(Socket socket) {
			this.socket = socket;
			// 通过Socket可以获取远端计算机的地址信息.
			InetAddress address = socket.getInetAddress();
			// 获取ip地址
			host = address.getHostAddress();
		}

		public void run() {
			// Socket提供的方法 InputStream getInputStream(),该方法可以获取一个输入流,从该流读取的数据就是从远端计算机发送过来的
			PrintWriter writer = null;
			try {
				InputStream input = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(input, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				OutputStream out = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
				writer = new PrintWriter(osw, true);
				// br.readLine()在读取客户端发送过来的消息时,由于客户端断线,而其操作系统的不同,
				// 这里读取的结果不同: 当windows的客户端断开时,br.readLine会抛出异常,当linux的客户端断开时,br.readLine会返回null
				nickName = br.readLine();
				System.out.println("主机名:" + host + ", " + nickName + " 上线了");
				// 将该客户端的输出流存入到共享集合中
				addToOut(nickName, writer);
				System.out.println("allOut元素数量:" + allOut.size());
				while (true) {
					String message = br.readLine();
					if (message == null) {
						break;
					}
					if (message.startsWith("@")) {
						sendOneMessage(nickName, message);
					} else {
						sendMessage("主机名:" + host + ", 昵称:" +  nickName + ", 发表内容:" + message);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
					removeOut(nickName);
					System.out.println(nickName + "断开连接");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
