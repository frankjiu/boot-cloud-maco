package com.socket.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.socket.domain.PushMessage;
import com.socket.service.SocketIOService;

@Service
public class SocketIOServiceImpl implements SocketIOService {
	
	// 用来存已连接的客户端
	private static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();
	
	// 推送到相应客户端
		private static Map<String, List<String>> codeClientMap = new ConcurrentHashMap<String, List<String>>();
	
	@Autowired
	private SocketIOServer socketIOServer;

	/**
	 * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
	 */
	@PostConstruct
	private void autoStartup() throws Exception {
		start();
	}

	/**
	 * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
	 */
	@PreDestroy
	private void autoStop() throws Exception {
		stop();
	}

	@Override
	public void start() {
		
		// 监听客户端连接
		socketIOServer.addConnectListener(client -> {
			//String loginUserNum = getParamsByClient(client);
			/*System.out.println("客户端已连接");
			if (loginUserNum != null) {
				System.out.println("连接数量为:" + loginUserNum);
				clientMap.put(loginUserNum, client);
			}*/
			
			String loginUserNum = getParamsByClient(client);
			String uniqueCode = getParamsByClient(client, "uniqueCode");
			
			System.out.println("========客户端getSessionId====>>" + client.getSessionId());
			if (loginUserNum != null) {
				System.out.println("========客户端连接====>>" + loginUserNum);
				clientMap.put(loginUserNum, client);
			}
			
			if (uniqueCode != null) {
				System.out.println("========uniqueCode====>>" + uniqueCode);
				List<String> codelist = codeClientMap.get(uniqueCode);
				if (codelist == null) {
					codelist = new CopyOnWriteArrayList<String>();
					codelist.add(loginUserNum);
				} else if (codelist != null) {
					codelist.add(loginUserNum);
				}
				codeClientMap.put(uniqueCode, codelist);
				System.out.println("===msgcode=====客户端连接====>>" + codelist.size());
			}
			
		});
		
		// 监听客户端断开连接
		socketIOServer.addDisconnectListener(client -> {
			String loginUserNum = getParamsByClient(client);
			if (loginUserNum != null) {
				clientMap.remove(loginUserNum);
				client.disconnect();
			}
		});
		
		// 处理自定义的事件，与连接监听类似
		socketIOServer.addEventListener(PUSH_EVENT, PushMessage.class, (client, data, ackSender) -> {
			System.out.println("处理自定义事件...");
		});
		
		socketIOServer.start();
	}

	@Override
	public void stop() {
		if (socketIOServer != null) {
			socketIOServer.stop();
			socketIOServer = null;
		}
	}

	/**
	 * 向客户端发送数据
	 */
	@Override
	public void pushMessageToUser(String uniqueCode, PushMessage pushMessage) {
		/*String loginUserNum = pushMessage.getLoginUserNum();
		System.out.println("准备向客户端传送数据");
		if (StringUtils.isNotBlank(loginUserNum)) {
			SocketIOClient clients = clientMap.get(loginUserNum);
			if (clients != null){
				clients.sendEvent(PUSH_EVENT, pushMessage);
			}
			
		}*/
		
		
		List<String> clients = codeClientMap.get(uniqueCode);
		if (clients != null) {
			for (String cliid : clients) {
				// log.info(new Date() + ",,,,cliid===" + cliid + ",,,,map===" +
				// clientMap.get(cliid));
				if (clientMap.get(cliid) != null) {
					// 判断是客户端是否开启
					if (clientMap.get(cliid).isChannelOpen()) {
						// log.info("=======发送到=====>>"+cliid+"=========>>"+message.getMsg());
						clientMap.get(cliid).sendEvent(PUSH_EVENT, pushMessage);
						// 当 断线的时候把客户端删除
					} else if (!clientMap.get(cliid).isChannelOpen()) {
						// log.info("=======客户端通道关闭之后就了连接就删除=====>>"+cliid);
						clientMap.remove(cliid);
					}
				}
			}
		}
	}
		
	/**
	 * 此方法为获取client连接中的参数,可根据需求更改
	 * 获取连接数
	 */
	private String getParamsByClient(SocketIOClient client) {
		// 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
		Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
		List<String> list = params.get("loginUserNum");
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	private String getParamsByClient(SocketIOClient client,String name) {
		// 从请求的连接中拿出参数（这里的 <loginUserNum> 必须是唯一标识）
		Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
		List<String> list = params.get(name);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}