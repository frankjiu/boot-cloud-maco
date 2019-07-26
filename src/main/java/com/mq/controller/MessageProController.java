package com.mq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mq.service.ProducerService;

@RestController
@RequestMapping("/message")
public class MessageProController {
	@Autowired
	private ProducerService producerService;
	
	@Autowired
	private Destination destination;
	
	/**
	 * queue点对点消息发送到指定 队列
	 * 
	 * @param msg
	 * @return
	 */
	@RequestMapping("/queue")
	public Object order(String msg) {
		List<String> list = new ArrayList<>();
		list.add("aing");
		list.add("bing");
		list.add("cing");
		list.add("ding");
		list.add("eing");
		
		Map<String, List<String>> mapMsg = new HashMap<>();
		mapMsg.put("uniqueCode", list);
		producerService.sendMessageQueue(destination, mapMsg);
		return "queue has been sent.";
	}

	/**
	 * topic发布订阅消息进行广播
	 * 
	 * @param msg
	 * @return
	 */
	@RequestMapping("/topic")
	public Object publish(String msg) {
		msg = "话题:今早重磅新闻!!!";
		producerService.publishTopic(msg);
		return "topic has been published!";
	}
}