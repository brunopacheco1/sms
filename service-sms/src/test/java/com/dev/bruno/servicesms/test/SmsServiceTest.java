package com.dev.bruno.servicesms.test;

import javax.ejb.embeddable.EJBContainer;

import org.junit.Test;

import com.dev.bruno.servicesms.exception.AppException;
import com.dev.bruno.servicesms.service.SmsService;

import junit.framework.TestCase;

public class SmsServiceTest extends TestCase {

	private EJBContainer ejbContainer;

	private SmsService service;

	@Override
	protected void setUp() throws Exception {
		ejbContainer = EJBContainer.createEJBContainer();
		Object object = ejbContainer.getContext().lookup("java:global/service-sms/SmsService");
		
		assertTrue(object instanceof SmsService);

		service = (SmsService) object;
		
		//INJETAR DEPENDENCIAS
	}

	protected void tearDown() throws Exception {
		ejbContainer.close();
	}

	@Test(expected=AppException.class)
	public void testQueueSmsNulo() throws Exception {
		service.queue(null);
	}
	
	@Test(expected=AppException.class)
	public void testSendSmsNulo() throws Exception {
		service.send(null);
	}
}