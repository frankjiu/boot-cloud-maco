package com.mq.domain;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.constant.Constants;

/**
 * Topic消息消费者
 * 
 * @author xinbe
 *
 */
@Component
public class TopicListenerAndConsumer {
	
	@JmsListener(destination = Constants.TOPIC_NAME, containerFactory = "jmsListenerContainerTopic")
	public void receive1(String text) {
		System.out.println("话题消息消费者a: " + text);
	}

	@JmsListener(destination = Constants.TOPIC_NAME, containerFactory = "jmsListenerContainerTopic")
	public void receive2(String text) {
		System.out.println("话题消息消费者b: " + text);
	}

	@JmsListener(destination = Constants.TOPIC_NAME, containerFactory = "jmsListenerContainerTopic")
	public void receive3(String text) {
		System.out.println("话题消息消费者c:" + text);
	}
	
	/*@Override
	public void run() {
		while (true) {
			try {
				receive(text);
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}*/
	
	
	
}