package com.mq.service.impl;

import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.mq.service.ProducerService;

/**
 * @author xinbe
 * 
 * 消息生产者实现类
 * 
 * 发送特定消息到指定队列
 * jmsTemplate 用于装载消息的broker容器
 * msg 待发消息
 * destination 发送队列
 * 
 */
@Service
public class ProducerServiceImpl implements ProducerService {
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	/**
	 * queue 需要指定队列名称
	 */
	@Override
	public void sendMessageQueue(Destination destination, Map<String, List<String>> mapMsg) {
		//ThreadLocal<JmsMessagingTemplate> threadLocal = new ThreadLocal<>();
		//threadLocal.set(jmsTemplate);
		jmsTemplate.convertAndSend(destination, mapMsg);
	}
	
	/**
	 * topic 需要指定话题名称
	 */
	@Autowired
	private Topic topic;

	@Override
	public void publishTopic(String msg) {
	   jmsTemplate.convertAndSend(topic, msg);
	}
	
}