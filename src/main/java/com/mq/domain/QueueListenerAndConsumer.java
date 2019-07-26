package com.mq.domain;

import java.util.List;
import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.constant.Constants;

/**
 * Queue消息消费者
 * 
 * @author xinbe
 *
 */
@Component
public class QueueListenerAndConsumer {
	
	//@Autowired
	//Session session;
	
	// 接收某队列中的消息
	@JmsListener(destination = Constants.QUEUE_DESTINATION, containerFactory = "jmsListenerContainerQueue")
	public void receiveQueue(Map<String, List<String>> mapMsg) {
		//text.acknowledge();// 手动签收模式, 该message在上一步不需要进行convert转换, 且需手动调用才能接收消息
		//session.recover();// 此处不调用recover则该消息将在服务重启后重发
		List<String> list = mapMsg.get("uniqueCode");
		StringBuffer stb = new StringBuffer();
		stb.append("uniqueCode");
		for (String e : list) {
			stb.append(e);
		}
		System.out.println("队列 <" + Constants.QUEUE_DESTINATION + "> 中传来消息:" + stb.toString());
		
	}
}