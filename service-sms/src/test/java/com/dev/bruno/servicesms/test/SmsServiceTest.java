package com.dev.bruno.servicesms.test;

import javax.ejb.embeddable.EJBContainer;

import org.junit.Test;

import com.dev.bruno.servicesms.dto.SentSmsDTO;
import com.dev.bruno.servicesms.exception.AppException;
import com.dev.bruno.servicesms.service.SmsService;

import junit.framework.TestCase;

public class SmsServiceTest extends TestCase {

	private EJBContainer container;

	private SmsService service;

	@Override
	protected void setUp() throws Exception {
		container = EJBContainer.createEJBContainer();
		Object object = container.getContext().lookup("java:global/service-sms/SmsService");
		
		assertTrue(object instanceof SmsService);

		service = (SmsService) object;
	}
	
	@Override
	protected void tearDown() throws Exception {
		container.close();
	}

	@Test
	public void testQueue() {
	    SentSmsDTO dto = null;
	    
	    queue(dto);
	    
	    dto = new SentSmsDTO();

	    queue(dto);
	    
	    dto.setTo("5521999112222");

	    queue(dto);
	    
	    dto.setTo("+5521999112222");
	    dto.setFrom("5521999112222");
	    
	    queue(dto);
	    
	    dto.setTo("+5521999112222");
	    dto.setFrom("+5521999112222");
	    
	    queue(dto);
	    
	    dto.setTo("+5521999112222");
	    dto.setFrom("+5521999112222");
	    dto.setBody("ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
	    
        queue(dto);
	}
	
	private void queue(SentSmsDTO dto) {
	    try {
		    service.queue(null);
		} catch(Exception e) {
		    assertTrue(e instanceof AppException);
		}
	}
	
	@Test
	public void testSend() {
		SentSmsDTO dto = null;
	    
	    send(dto);
	    
	    dto = new SentSmsDTO();

	    send(dto);
	    
	    dto.setTo("5521999112222");

	    send(dto);
	    
	    dto.setTo("+5521999112222");
	    dto.setFrom("5521999112222");
	    
	    send(dto);
	    
	    dto.setTo("+5521999112222");
	    dto.setFrom("+5521999112222");
	    
	    send(dto);
	    
	    dto.setTo("+5521999112222");
	    dto.setFrom("+5521999112222");
	    dto.setBody("ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
	    
        send(dto);
	}
	
	private void send(SentSmsDTO dto) {
	    try {
		    service.send(null);
		} catch(Exception e) {
		    assertTrue(e instanceof AppException);
		}
	}
}