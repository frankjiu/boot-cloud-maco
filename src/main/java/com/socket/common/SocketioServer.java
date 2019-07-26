package com.socket.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

/**
 * 
 */
public class SocketioServer implements Runnable {
	// 静态消息列表，SocketDataObj实体类 userid，message getter&setter
	private static LinkedList<SocketDataObj> msgList = new LinkedList<SocketDataObj>();
	// server配置容器
	private static Configuration conf = null;
	// server已配置标识
	private static boolean conf_flag = false;
	// server已启动标识
	private static boolean server_flag = false;
	// server
	private static SocketIOServer server = null;
	// 客户端暂存
	private static HashMap<String, SocketIOClient> client_cache = new HashMap<String, SocketIOClient>();
	// 用户客户端关系暂存
	private static HashMap<UUID, String> user_client_cache = new HashMap<UUID, String>();

	// 构造器
	public SocketioServer(List<SocketDataObj> list) {
		if (!list.isEmpty()) {
			synchronized (msgList) {
				for (SocketDataObj s : list) {
					SocketioServer.msgList.add(s);
					msgList.notify(); // 当有新的数据放入时，释放锁
				}
			}
		}
	}

	// 线程执行器
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {// 打开线程
			synchronized (msgList) { // 获取锁
				if (!server_flag) {
					this.getConfig();
					server = new SocketIOServer(conf);
					this.startServer();
				}
				if (!msgList.isEmpty()) { // 如果消息列表有数据，则需要推送
					SocketDataObj sdo = msgList.getFirst();
					String userid = sdo.getId();
					SocketIOClient client = client_cache.get(userid);
					if (client != null) {
						client.sendEvent("msg", sdo.getMessage());
						msgList.removeFirst();
					}
				} else {
					try {
						msgList.wait(); // 如果消息列表无数据则wait 保持线程
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 生成配置容器
	private void getConfig() {
		if (!conf_flag) {
			conf = new Configuration();
			try {
				// String hostName = PropertiesReader.getValByKey("socketioIp");
				// String port = PropertiesReader.getValByKey("socketPort");
				String hostName = "localhost";
				String port = "9999";
				if (hostName != null && !StringUtils.isEmpty(hostName) && port != null && !StringUtils.isEmpty(port)) {
					conf.setHostname(hostName);
					conf.setPort(Integer.parseInt(port));
					conf_flag = true;
				}
			} catch (Exception e) {
				try {
					throw new Exception("获取配置信息出错！");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	// 启动server增加监听器
	private void startServer() {
		if (!server_flag) {
			this.getConfig();
			server.start();
			server_flag = true;
			server.addConnectListener(new ConnectListener() {
				@Override
				public void onConnect(SocketIOClient client) {
					// TODO Auto-generated method stub
				}
			});
			server.addDisconnectListener(new DisconnectListener() {
				@Override
				public void onDisconnect(SocketIOClient client) {
					// TODO Auto-generated method stub
					// 根据客户端sessionID获取用户与client缓存中的信息
					String userid = user_client_cache.get(client.getSessionId());
					if (userid != null) {
						if (client_cache.get(userid).getSessionId().equals(client.getSessionId())) { // 如果当前缓存中的client就是断开的client
							// 清除当前信息
							client_cache.remove(userid);
						}
						// 清除关系缓存中的信息
						user_client_cache.remove(client.getSessionId());
					}
				}
			});
			// 增加regId监听,regId是与前台监听名对应一致的。当前台客户端连接上server后，向后台发送一个regId值，后台将这个值作为该客户端的唯一标识，并与client绑定存储
			// regInfo实体类中只有一个属性就是userId + getter&setter
			server.addEventListener("regId", RegInfo.class, new DataListener<RegInfo>() {
				// 实现dataListener的回调
				@Override
				public void onData(SocketIOClient client, RegInfo data, AckRequest ackSender) throws Exception {
					// TODO Auto-generated method stub
					// userid即为前台传到后台的regId
					String userid = data.getUserId();
					if (userid != null && StringUtils.isNotEmpty(userid)) {
						// 由于一个客户端是外部轮询的，所以一直在变化
						// 在客户端缓存中清除原有属于该userid的客户端
						client_cache.remove(userid);
						// 增加新的客户端
						client_cache.put(userid, client);
						// 向用户与客户端存储中存入新的client
						user_client_cache.put(client.getSessionId(), userid);
						client.sendEvent("regOK", data);
						System.out.println("注册成功！key=" + userid);
					}
				}
			});
		}
	}

	@SuppressWarnings("unused")
	private void stopServer() {
		if (server_flag) {
			server.stop();
			server_flag = false;
		}
	}
}
