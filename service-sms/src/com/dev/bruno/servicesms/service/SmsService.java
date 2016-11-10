package com.dev.bruno.servicesms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.dev.bruno.servicesms.dao.SmsDAO;
import com.dev.bruno.servicesms.dto.ResultDTO;
import com.dev.bruno.servicesms.dto.SentSmsDTO;
import com.dev.bruno.servicesms.dto.SmsDTO;
import com.dev.bruno.servicesms.exception.AppException;
import com.dev.bruno.servicesms.model.Sms;
import com.dev.bruno.servicesms.queue.SmsQueue;

@Stateless
public class SmsService {

    private Logger logger = Logger.getLogger(getClass().getName());
    
	@Inject
	private SmsDAO dao;
	
	@Inject
	private SmsQueue queue;
	
	@Inject
	private OperadoraFactoryService operadoraFactory;
	
	public ResultDTO list(String queryStr, Integer start, Integer limit, String order, String dir) throws Exception {
		if(start == null) {
			start = 0;
		}
		
		if(limit == null) {
			limit = 100;
		}
		
		if(order == null) {
			order = "id";
		}
		
		if(dir == null) {
			dir = "asc";
		}
		
		List<SmsDTO> dtos = new ArrayList<>();
		
		for(Sms sms : dao.list(queryStr, start, limit, order, dir)) {
			dtos.add(entityToDTO(sms));
		}
		
		ResultDTO result = new ResultDTO();
		
		result.setResultSize((long) dtos.size());
		result.setTotalSize(dao.count(queryStr));
		result.setResult(dtos);
		result.setDir(dir);
		result.setLimit(limit);
		result.setOrder(order);
		result.setStart(start);
		
		return result;
	}
	
	public SmsDTO get(Long id) throws Exception {
		return entityToDTO(dao.get(id));
	}
	
	public void send(SentSmsDTO dto) throws Exception {
		validate(dto);
		
		Sms sms = dtoToEntity(dto);
		
		if(sms.getValidDate() != null && sms.getValidDate().before(new Date())) {
			sms.setInvalidationDate(new Date());
		} else {
            OperadoraService service = operadoraFactory.getOperadora(dto.getTo());
            
            try {
                service.send(dto);
                sms.setSentDate(new Date());                
            } catch(Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
                
                sms.setFailureDate(new Date());
                sms.setFailureMsg(e.getMessage().length() > 1000 ? e.getMessage().substring(0, 1000) : e.getMessage());
            }
		}
		
		dao.add(sms);
	}
	
	public void queue(SentSmsDTO dto) throws Exception {
		validate(dto);
		
		queue.publish(dto);
	}

	
	private void validate(SentSmsDTO dto) throws AppException {
	    if(dto == null) {
	        throw new AppException("SMS é obrigatório.");
	    }
	    
	    if(StringUtils.isBlank(dto.getTo()) || !dto.getTo().matches("^\\+\\d{13}$")) {
	        throw new AppException("O formato do campo to deve ser o seguinte: ^\\+\\d{13}$. Ex: +5521999112222");
		}
		
		if(StringUtils.isBlank(dto.getFrom()) || !dto.getFrom().matches("^\\+\\d{13}$")) {
	        throw new AppException("O formato do campo from deve ser o seguinte: ^\\+\\d{13}$. Ex: +5521999112222");
		}
		
		if(StringUtils.isBlank(dto.getBody()) || dto.getBody().length() > 160) {
	        throw new AppException("O campo body não pode ser maior que 160 carateres.");
		}
	}
	
	private SmsDTO entityToDTO(Sms sms) throws Exception {
		SmsDTO dto = new SmsDTO();
		
		if(sms == null) {
			return dto;
		}
		
		PropertyUtils.copyProperties(dto, sms);
		
		return dto;
	}
	
	private Sms dtoToEntity(SentSmsDTO dto) throws Exception {
		Sms sms = new Sms();
		
		if(dto == null) {
			return sms;
		}
		
		PropertyUtils.copyProperties(sms, dto);
		
		return sms;
	}
}