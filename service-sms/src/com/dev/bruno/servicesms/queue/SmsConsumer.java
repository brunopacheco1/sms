package com.dev.bruno.servicesms.queue;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dev.bruno.servicesms.dto.SmsDTO;
import com.dev.bruno.servicesms.service.ServiceLocator;
import com.dev.bruno.servicesms.service.SmsService;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class SmsConsumer extends DefaultConsumer {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	public SmsConsumer(Channel channel) {
		super(channel);
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
		String message = new String(body, "UTF-8");
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		SmsDTO dto = builder.create().fromJson(message, SmsDTO.class);
		
		SmsService smsService = (SmsService) ServiceLocator.getInstance().lookup(SmsService.class);
		
		try {
			smsService.send(dto);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}