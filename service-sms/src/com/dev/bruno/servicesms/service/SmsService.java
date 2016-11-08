package com.dev.bruno.servicesms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.beanutils.PropertyUtils;

import com.dev.bruno.servicesms.dao.SmsDAO;
import com.dev.bruno.servicesms.dto.ResultDTO;
import com.dev.bruno.servicesms.dto.SentSmsDTO;
import com.dev.bruno.servicesms.dto.SmsDTO;
import com.dev.bruno.servicesms.model.Sms;

@Stateless
public class SmsService {

	@Inject
	private SmsDAO dao;
	
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
		Sms sms = dtoToEntity(dto);
		
		if(sms.getValidDate() != null && sms.getValidDate().before(new Date())) {
			sms.setInvalidationDate(new Date());
		} else {
			sms.setSentDate(new Date());
		}
		
		dao.add(sms);
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