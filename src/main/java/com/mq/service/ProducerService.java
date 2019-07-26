package com.mq.service;

import java.util.List;
import java.util.Map;

import javax.jms.Destination;

/**
 * 消息生产者接口
 * @author xinbe
 *
 */
public interface ProducerService {
	
	public void sendMessageQueue(Destination destination, Map<String, List<String>> mapMsg);

	public void publishTopic(String msg);

}