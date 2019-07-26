package com.socket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.constant.Constants;
import com.socket.domain.PushMessage;
import com.socket.service.SocketIOService;

@Controller
@RequestMapping("/nettysocket")
public class SocketIoController {
	
	@Autowired
	private  SocketIOService socketIOService;
	
	/**
	 * 转到io请求页
	 * @return
	 */
	@RequestMapping("/start")
	public String startIo(){
		try {
			return "/socket_io";
		} catch (Exception e) {
			e.printStackTrace();
			return "no";
		}
	}
	
	/**
	 * 监听消费数据(数据生产由mq模块的生产者负责)
	 */
	@JmsListener(destination = Constants.QUEUE_DESTINATION, containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue(Map<String, List<String>> mapMsg) {
		//text.acknowledge();// 手动签收模式, 该message在上一步不需要进行convert转换, 且需手动调用才能接收消息
		//session.recover();// 此处不调用recover则该消息将在服务重启后重发
		System.out.println("监听到数据:" + mapMsg);
		
		String uniqueCode = "uniqueCode";
		if (mapMsg != null) {
			// 这里将mapMsg解析为pushMessage格式
			PushMessage pushMessage = new PushMessage();
			pushMessage.setLoginUserNum("50");
			pushMessage.setContent("内部数据");
			if (pushMessage != null) {
				//Object message = pushMessage.getContent();
				//if (message != null) {
					//JSONObject jsonb = JSONObject.parseObject(message.toString());
					//pushMessage = JSONObject.toJavaObject(jsonb, PushMessage.class);
					socketIOService.pushMessageToUser(uniqueCode, pushMessage);
				//}
			}
		}
	}
	
}
