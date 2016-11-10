package com.dev.bruno.servicesms.service;

import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class PropertiesService {
    
    private Properties props;
    
    @PostConstruct
	private void load() throws Exception {
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