package com.dev.bruno.servicesms.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.apache.commons.beanutils.PropertyUtils;

import com.dev.bruno.servicesms.dto.SentSmsDTO;

public class M4UOperadoraService extends OperadoraService {
    
    @Override
    public void send(SentSmsDTO dto) throws Exception {
        M4USms sms = new M4USms(); 
        
        PropertyUtils.copyProperties(sms, dto);
        
        Client client = ClientBuilder.newClient();
        
        client.target("http://recruitment.m4u.com.br/api/v1").path("/sms").request(MediaType.APPLICATION_JSON).post(Entity.json(sms));
        
        logger.info(String.format("M4U: Enviando SMS de %s para %s...", sms.getFrom(), sms.getTo()));
    }
    
    public class M4USms {
        private String from;
        private String to;
    	private String body;
    	
    	public String getFrom() {
    		return from;
    	}
    
    	public void setFrom(String from) {
    		this.from = from;
    	}
    
    	public String getTo() {
    		return to;
    	}
    
    	public void setTo(String to) {
    		this.to = to;
    	}
    
    	public String getBody() {
    		return body;
    	}
    
    	public void setBody(String body) {
    		this.body = body;
    	}
    }
}
