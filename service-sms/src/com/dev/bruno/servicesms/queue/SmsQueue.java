package com.dev.bruno.servicesms.queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;

import com.dev.bruno.servicesms.dto.SmsDTO;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Singleton
public class SmsQueue {

	@Resource(lookup="msg.broker.host")
	private String host;
	
	@Resource(lookup="msg.broker.queue")
	private String queue;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private Connection connection;
	
	private Channel channel;
	
	@PostConstruct
	private void connect() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost(host);
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(queue, false, false, false, null);
		} catch (IOException | TimeoutException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	@PreDestroy
	private void disconnect() {
		try {
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public void add(SmsDTO dto) throws Exception {
		if(dto != null) {
			channel.basicPublish("", queue, null, new Gson().toJson(dto).getBytes());
		}
	}
}