package com.dev.bruno.servicesms.test;

import javax.ejb.embeddable.EJBContainer;

import org.junit.Test;

import com.dev.bruno.servicesms.service.OperadoraFactoryService;

import junit.framework.TestCase;

public class OperadoraFactoryServiceTest extends TestCase {

	private EJBContainer ejbContainer;

	private OperadoraFactoryService service;

	@Override
	protected void setUp() throws Exception {
		ejbContainer = EJBContainer.createEJBContainer();
		Object object = ejbContainer.getContext().lookup("java:global/service-sms/OperadoraFactoryService");
		
		assertTrue(object instanceof OperadoraFactoryService);

		service = (OperadoraFactoryService) object;
	}

	protected void tearDown() throws Exception {
		ejbContainer.close();
	}

	@Test
	public void testNullOperadora() {
		assertNull(service.getOperadora(null));
	}
	
	@Test
	public void testDestinatarioNoMatch() {
		assertNull(service.getOperadora("2222"));
	}
	
	@Test
	public void testDestinatarioMatch() {
		assertNotNull(service.getOperadora("+5521999112222"));
	}
}