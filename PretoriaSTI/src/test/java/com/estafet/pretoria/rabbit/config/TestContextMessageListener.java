/**
 * 
 */
package com.estafet.pretoria.rabbit.config;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.IOExceptionWithCause;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/test/webapp")
@ContextConfiguration(locations={"/WEB-INF/applicationContext.xml", "/WEB-INF/rabbitMQContext.xml"})
public class TestContextMessageListener {

	private static Logger theLogger = Logger.getLogger(TestContextMessageListener.class);
	/**
	 * @throws java.lang.Exception
	 */
	@Autowired
	private RabbitTemplate amqpTemplate; 
	
	@Autowired
	private MockedContextMessageListener mainContainerListener;
	
	//@Autowired
	//private ConnectionFactory connectionFactory;
	
	
	
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMessageListener() {
		try{
			theLogger.info("Entered testMessageListener....");
			List<Message> messages = mainContainerListener.getMessageList();
			assertNotNull(messages);
			sendMessageToAQueue();
			Thread.sleep(30000);
			assertTrue(messages.size() > 0);
		}catch(IOException ioe){
			theLogger.error("IOE exception has occured: ", ioe);
			ioe.printStackTrace();
			fail("Not yet implemented " + ioe.getMessage());	
		}catch(TimeoutException toe){
			theLogger.error("TOE exception has occured: ",toe);
			toe.printStackTrace();
			fail("Not yet implemented " + toe.getMessage());
		}catch(InterruptedException ie){
			
		}
	}
	
	private void sendMessageToAQueue() throws IOException, TimeoutException {
		theLogger.info("Entered preparing a message....");
		amqpTemplate.setQueue("temperatures");
		amqpTemplate.setExchange("direct");
		
		MessageProperties properties = new MessageProperties();
		Message message = new Message("10C".getBytes(), properties); 

		amqpTemplate.send("STIdevice", message);
		
	/*	Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("temperatures", false, false, false, null);
		String message = "10C";
		channel.basicPublish("", "STIdevice", null, message.getBytes());
		*/
	}

}
