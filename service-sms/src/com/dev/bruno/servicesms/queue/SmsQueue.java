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

import org.apache.commons.lang3.StringUtils;

import com.dev.bruno.servicesms.dto.SentSmsDTO;
import com.dev.bruno.servicesms.exception.AppException;
import com.dev.bruno.servicesms.resources.JacksonConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	public void send(SentSmsDTO dto) throws Exception {
	    validate(dto);
	    
	    ObjectMapper mapper = JacksonConfig.getObjectMapper();
	
	    channel.basicPublish("", queue, null, mapper.writeValueAsString(dto).getBytes());	
	}
	
	private void validate(SentSmsDTO dto) throws AppException {
	    if(dto == null) {
	        throw new AppException("SMS é obrigatório.");
	    }
	    
	    if(StringUtils.isBlank(dto.getTo()) || !dto.getTo().matches("^\\+\\d{13}$")) {
	        throw new AppException("O formato do campo to deve ser o seguinte: ^\\+\\d{13}$. Ex: +5521999112222");
		}
		
		if(StringUtils.isBlank(dto.getFrom()) || !dto.getFrom().matches("^\\+\\d{13}$")) {
	        throw new AppException("O formato do campo from deve ser o seguinte: ^\\+\\d{13}$. Ex: +5521999112222");
		}
		
		if(StringUtils.isBlank(dto.getBody()) || dto.getBody().length() > 160) {
	        throw new AppException("O campo body não pode ser maior que 160 carateres.");
		}
	}
}