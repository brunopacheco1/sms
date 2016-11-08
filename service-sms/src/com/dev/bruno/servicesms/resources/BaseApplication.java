package com.dev.bruno.servicesms.resources;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("api")
public class BaseApplication extends Application {
	
	public BaseApplication() {
		BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("ec2-54-92-181-133.compute-1.amazonaws.com");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage("com.dev.bruno.servicesms.resources");
        beanConfig.setScan(true);
	}
}