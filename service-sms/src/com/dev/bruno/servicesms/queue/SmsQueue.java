package com.dev.bruno.servicesms.queue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;

import com.dev.bruno.servicesms.dto.SmsDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
		    factory.setUri(host);
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.queueDeclare(queue, true, false, false, null);
			
			channel.basicConsume(queue, true, new SmsConsumer(channel));
			channel.basicConsume(queue, true, new SmsConsumer(channel));
		} catch (IOException | TimeoutException | KeyManagementException | NoSuchAlgorithmException | URISyntaxException e) {
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
	
	public void send(SmsDTO dto) throws Exception {
		if(dto != null) {
			GsonBuilder builder = new GsonBuilder();
			builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			channel.basicPublish("", queue, null, builder.create().toJson(dto).getBytes());
		}
	}
}