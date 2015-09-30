package com.estafet.pretoria.rabbit.config;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class ContextMessageListener implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(ContextMessageListener.class); 
	
	public void onMessage(Message message){
		LOGGER.debug("!!! A message received " + new String(message.getBody()));
		System.out.println("!!! A message received " + new String(message.getBody()));
	}
}
