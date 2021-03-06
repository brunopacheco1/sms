package com.dev.bruno.servicesms.test;

import javax.ejb.embeddable.EJBContainer;

import org.junit.Test;

import com.dev.bruno.servicesms.dto.SentSmsDTO;
import com.dev.bruno.servicesms.exception.AppException;
import com.dev.bruno.servicesms.service.SmsService;

import junit.framework.TestCase;

public class SmsServiceQueueTest extends TestCase {
    
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
	public void testNull() {
		SentSmsDTO dto = null;
	    
	    queue(dto);
    }
    
	@Test
	public void testEmpty() {
	    SentSmsDTO dto = new SentSmsDTO();

	    queue(dto);
    }

	@Test
	public void testToNoMatch() {
	    SentSmsDTO dto = new SentSmsDTO();
	    dto.setTo("5521999112222");

	    queue(dto);
	}

	@Test
	public void testFromNoMatch() {
	    SentSmsDTO dto = new SentSmsDTO();    
	    dto.setTo("+5521999112222");
	    dto.setFrom("5521999112222");
	    
	    queue(dto);
	}

	@Test
	public void testBodyNoMatch() {
	    SentSmsDTO dto = new SentSmsDTO();    
	    dto.setTo("+5521999112222");
	    dto.setFrom("+5521999112222");
	    dto.setBody("ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
	    
        queue(dto);
	}
	
	private void queue(SentSmsDTO dto) {
	    try {
		    service.queue(dto);
		} catch(Exception e) {
		    assertTrue(e instanceof AppException);
		}
	}
}
