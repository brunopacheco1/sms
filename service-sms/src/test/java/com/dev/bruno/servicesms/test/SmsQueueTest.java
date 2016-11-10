package com.dev.bruno.servicesms.test;

import javax.ejb.embeddable.EJBContainer;

import org.junit.Test;

import com.dev.bruno.servicesms.exception.AppException;
import com.dev.bruno.servicesms.queue.SmsQueue;

import junit.framework.TestCase;

public class SmsQueueTest extends TestCase {

	private EJBContainer container;

	private SmsQueue queue;

	@Override
	protected void setUp() throws Exception {
		container = EJBContainer.createEJBContainer();
		Object object = container.getContext().lookup("java:global/service-sms/SmsQueue");
		
		assertTrue(object instanceof SmsQueue);

		queue = (SmsQueue) object;
	}
	
	@Override
	protected void tearDown() throws Exception {
		container.close();
	}

	@Test(expected=AppException.class)
	public void testPublish() throws Exception {
		queue.publish(null);
	}
}