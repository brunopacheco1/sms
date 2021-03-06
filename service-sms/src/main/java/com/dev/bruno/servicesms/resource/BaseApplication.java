package com.dev.bruno.servicesms.resource;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("api")
public class BaseApplication extends Application {
	
	public BaseApplication() {
		BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setContact("brunopacheco1@yahoo.com");
        beanConfig.setTitle("Serviço de Envio de SMS");
        beanConfig.setDescription("Projeto RESTFul conceito de envio de SMS. Desenvolvido em Java, utilizando a especificação JavaEE 7 (JAX-RS, EJB e JPA).");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("ec2-54-167-228-148.compute-1.amazonaws.com");
        beanConfig.setBasePath("/service-sms/api");
        beanConfig.setResourcePackage("com.dev.bruno.servicesms.resource");
        beanConfig.setScan(true);
	}
}