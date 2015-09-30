package com.estafet.pretoria.rabbit.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class MockedContextMessageListener implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(MockedContextMessageListener.class);
	private List<Message> messages;
	
	public MockedContextMessageListener(){
		messages = new ArrayList<Message>();
	}
	
	
	public void onMessage(Message message){
		LOGGER.debug("!!! A message received " + new String(message.getBody()));
		System.out.println("!!! A message received " + new String(message.getBody()));
		messages.add(message);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Message> getMessageList(){
		return messages;
	}

}
