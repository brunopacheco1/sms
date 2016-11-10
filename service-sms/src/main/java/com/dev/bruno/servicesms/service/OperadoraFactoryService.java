package com.dev.bruno.servicesms.service;

import java.util.Random;
import java.util.logging.Logger;

import javax.ejb.Singleton;

import org.apache.commons.lang3.StringUtils;

@Singleton
public class OperadoraFactoryService {

    protected Logger logger = Logger.getLogger(getClass().getName());
    
    public OperadoraService getOperadora(String to) {
        if(StringUtils.isBlank(to) || !to.matches("^\\+\\d{13}$")) {
            return null;
        }
        
        Random random = new Random();
        
        OperadoraService operadoraService = null;
        
        //SIMULAR ALTERNANCIA ENTRE OPERADORAS
        switch (random.nextInt(6) + 1) {
            case 1:
            case 2:
                operadoraService = new ClaroService();            
                break;
            case 3:
            case 4:
                operadoraService = new TimService();
                break;
            default:
                operadoraService = new M4UOperadoraService();
                break;
        }
        
        return operadoraService;
    }
}