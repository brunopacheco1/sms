package com.dev.bruno.servicesms.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

@Singleton
public class PropertiesService {
    
    private Properties props;
    
    @PostConstruct
	private void load() {
	    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("service-sms.properties");
	    
	    props = new Properties();
        props.load(inputStream);
    }

    public String getProperty(String key) {
        if(key == null) {
            return null;
        }
        
        return props.getProperty(key);
    }
}