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
import javax.ejb.Singleton;
import javax.inject.Inject;

import com.dev.bruno.servicesms.dto.SentSmsDTO;
import com.dev.bruno.servicesms.exception.AppException;
import com.dev.bruno.servicesms.resource.JacksonConfig;
import com.dev.bruno.servicesms.service.PropertiesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Singleton
public class SmsQueue {

    @Inject
    private PropertiesService properties;
    
	private String host;
	
	private String queue;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private Connection connection;
	
	private Channel channel;
	
	@PostConstruct
	private void connect() {
		try {
		    host = properties.getProperty("msg.broker.host");
		    queue = properties.getProperty("msg.broker.queue");
		    
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
	
	public void publish(SentSmsDTO dto) throws Exception {
		if(dto == null) {
			throw new AppException("Sms nulo.");
		}
		
	    ObjectMapper mapper = JacksonConfig.getObjectMapper();
	
	    channel.basicPublish("", queue, null, mapper.writeValueAsString(dto).getBytes());	
	}
}