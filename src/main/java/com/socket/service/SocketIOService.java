package com.socket.service;

import com.socket.domain.PushMessage;

public interface SocketIOService {
	// 推送的事件
	public static final String PUSH_EVENT = "push_event";

	// 启动服务
	void start() throws Exception;

	// 停止服务
	void stop();

	// 推送信息
	void pushMessageToUser(String uniqueCode, PushMessage pushMessage);
}