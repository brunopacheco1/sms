package com.dev.bruno.servicesms.service;

import java.util.logging.Logger;

import com.dev.bruno.servicesms.dto.SentSmsDTO;

public abstract class OperadoraService {
    
    protected Logger logger = Logger.getLogger(getClass().getName());
    	
    public abstract void send(SentSmsDTO dto) throws Exception;
}