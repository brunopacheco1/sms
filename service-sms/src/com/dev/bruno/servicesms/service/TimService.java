package com.dev.bruno.servicesms.service;

import com.dev.bruno.servicesms.dto.SentSmsDTO;

public class TimService extends OperadoraService {
    
    @Override
    public void send(SentSmsDTO dto) {
        logger.info(String.format("Tim: Enviando SMS de %s para %s...", dto.getFrom(), dto.getTo()));
    }
}