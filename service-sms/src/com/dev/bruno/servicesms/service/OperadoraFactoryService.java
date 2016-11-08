package com.dev.bruno.servicesms.service;

import java.util.logging.Logger;

import javax.ejb.Singleton;

import org.apache.commons.lang3.StringUtils;

@Singleton
public class OperadoraFactoryService {

    protected Logger logger = Logger.getLogger(getClass().getName());
    
    //SIMULAR ALTERNANCIA ENTRE OPERADORAS
    private boolean claro = false;
    
    public OperadoraService getOperadora(String to) {
        if(StringUtils.isBlank(to) || !to.matches("^\\+\\d{13}$")) {
            return null;
        }
        
        logger.info("BUSCANDO OPERADORA DO DESTINATARIO...");
        
        if(claro) {
            claro = false;
            return new ClaroService();
        } else {
            claro = true;
            return new TimService();            
        }
    }
}